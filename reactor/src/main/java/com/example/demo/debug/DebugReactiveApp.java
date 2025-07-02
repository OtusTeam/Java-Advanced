package com.example.demo.debug;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DebugReactiveApp {
    public static final EmailProviderDto[] emailProviders = new EmailProviderDto[]{
            new EmailProviderDto(1, "mail.ru"),
            new EmailProviderDto(2, "yandex.ru"),
            new EmailProviderDto(3, "gmail.com")
    };

    public static final UserDto[] users = new UserDto[]{
            new UserDto(1, "Ivan", "ivan1@mail.ru"),
            new UserDto(2, "Evgeny", "evgeny.v@yandex.ru"),
            new UserDto(3, "Lisa", "lisa1998@gmail.com"),
            new UserDto(4, "John", "john.doe@gmail.com"),
            new UserDto(5, "Kate", "katakate@gmail.com")
    };

    public void main(String[] args) {


        DebugReactiveApp app = new DebugReactiveApp();

        // ✅ Асинхронный вызов
        app.getUsersEmailProviders(app.getAllUsers())
                .subscribe(i -> System.out.println("Async EmailProvider.id = " + i));

        // ✅ Синхронный вызов
//        app.getUsersEmailProviders(app.getAllUsers())
//                .collectList()
//                .block()
//                .forEach(i -> System.out.println("Sync EmailProvider.id = " + i));
    }

    public Flux<Integer> getUsersEmailProviders(Flux<UserDto> users) {
        return users
                .map(UserDto::getEmail)
                .filter(email -> email != null && email.contains("@"))
                .groupBy(u -> u.split("@")[1])
                .checkpoint("Extracting email provider")
                .flatMap(g -> g.reduce((a, b) -> a))
                .map(url -> getByUrl(url).map(EmailProviderDto::getId))
                .flatMap(Function.identity());
    }

    public List<Integer> getUsersEmailProviders(List<UserDto> users) {
        final List<String> emails = new ArrayList<>(users.size());
        for (UserDto u : users)
            emails.add(u.getEmail().split("@")[1]);

        final Set<String> urls = new HashSet<>(emails);
        final List<EmailProviderDto> providers = new ArrayList<>(urls.size());
        for (String url : urls) {
            final EmailProviderDto provider = getByUrl(url).block();
            providers.add(provider);
        }

        return providers.stream()
                .map(EmailProviderDto::getId)
                .toList();
    }

    private Flux<UserDto> getAllUsers() {
        return Flux.just(users);
    }

    private Mono<EmailProviderDto> getByUrl(String url) {
        return Flux.just(emailProviders)
                .filter(p -> url.equalsIgnoreCase(p.getFqdn()))
                .singleOrEmpty();
    }

}