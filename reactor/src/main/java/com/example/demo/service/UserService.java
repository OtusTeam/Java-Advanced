package com.example.demo.service;

import com.example.demo.debug.EmailProviderDto;
import com.example.demo.dto.User;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {
    public static final EmailProviderDto[] emailProviders = new EmailProviderDto[]{
            new EmailProviderDto(1, "mail.ru"),
            new EmailProviderDto(2, "yandex.ru"),
            new EmailProviderDto(3, "gmail.com")
    };

    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    public Flux<User> all() {
        return repository.findAll();
    }

    public Mono<User> get(Integer id) {
        return repository.findById(id);
    }

    public Flux<String> getUsersEmails() {
        return repository.getAllEmails();
    }

    public List<String> getUsersEmailsBlocking() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return List.of("alex@mail.ru", "anastasia@mail.ru", "gleb@mail.ru");
    }

    public Flux<Integer> getUsersEmailProviderIds() {
        return getUsersEmails()
                .doOnSubscribe(s -> System.out.println("🔥 Подписка на getUsersEmails()"))
                .doOnNext(email -> System.out.println("📩 Получен email: " + email))
//                .filter(email -> {
//                    boolean valid = email != null && email.contains("@");
//                    if (!valid) {
//                        System.err.println("⚠️ Невалидный email: " + email);
//                    }
//                    return valid;
//                })
                .map(email -> {
                    String domain = email.split("@")[1];
                    System.out.println("🌐 Выделен домен: " + domain);
                    return domain;
                })
                .groupBy(Function.identity())
                .checkpoint("📍 После groupBy — сгруппировали по домену")
                .flatMap(group -> group
                        .reduce((a, b) -> a) // берём один email из группы
                        .doOnNext(e -> System.out.println("✅ Из группы '" + group.key() + "' взят email: " + e))
                        .map(email -> group.key())
                )
                .doOnNext(domain -> System.out.println("🔍 Мапим домен в провайдера: " + domain))
                .flatMap(domain -> getByUrl(domain)
                        .doOnNext(p -> System.out.println("🎯 Провайдер найден: " + p.getFqdn() + " → ID " + p.getId()))
                        .doOnError(e -> System.err.println("💥 Ошибка при поиске провайдера: " + e.getMessage()))
                        .map(EmailProviderDto::getId)
                )
                .doOnNext(id -> System.out.println("📦 ID провайдера: " + id))
                .doOnError(e -> System.err.println("❌ Общая ошибка: " + e.getMessage()))
                .doOnComplete(() -> System.out.println("✅ Выполнение завершено"))
                .checkpoint("🏁 Конец getUsersEmailProviders")
                .log("🚨 LOG (getUsersEmailProviders)");
    }

    private Mono<EmailProviderDto> getByUrl(String url) {
        return Flux.fromArray(emailProviders)
                .filter(p -> url.equalsIgnoreCase(p.getFqdn()))
                .singleOrEmpty();
    }

    public Mono<User> update(Integer id, String name, String email) {
        return repository.findById(id)
                .map(u -> new User(id, name, email))
                .flatMap(repository::save);
    }

    public Mono<User> delete(Integer id) {
        return repository.findById(id)
                .flatMap(u -> repository.delete(u).thenReturn(u));
    }

    public Mono<User> create(String name, String email) {
        return repository.save(new User(null, name, email));
    }
}
