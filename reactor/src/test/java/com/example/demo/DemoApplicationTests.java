package com.example.demo;

import com.example.demo.dto.EmailProviderDto;
import com.example.demo.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.function.Function.identity;

@SpringBootTest
class DemoApplicationTests {

    private static final EmailProviderDto[] emailProviders = new EmailProviderDto[] {
            new EmailProviderDto(1, "mail.ru"),
            new EmailProviderDto(2, "yandex.ru"),
            new EmailProviderDto(3, "gmail.com"),
    };

    public static final UserDto[] users = new UserDto[] {
            new UserDto(1, "Ivan", "ivan1@mail.ru"),
            new UserDto(2, "Evgeny", "evgeny.v@yandex.ru"),
            new UserDto(3, "Lisa", "lisa1998@gmail.com"),
            new UserDto(4, "John", "john.doe@gmail.com"),
            new UserDto(5, "Kate", "katekate@gmail.com"),
    };

    public Flux<Integer> getUsersEmailProviders(Flux<UserDto> users) {
        return users
                .map(UserDto::getEmail)
                .map(e -> e.split("@")[1])
                .groupBy(identity())
                .flatMap(g -> g.reduce((a, b) -> a))
                .map(url -> getByUrl(url).map(EmailProviderDto::getId))
                .flatMap(identity());
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

        return providers.stream().map(EmailProviderDto::getId).toList();
    }

    @Test
    public void test() {
        getUsersEmailProviders(getAllUsers()).subscribe(i -> {
            System.out.println("Async EmailProvider.id = " + i);
        });

//        getUsersEmailProviders(getAllUsers().collectList().block()).forEach(i -> {
//            System.out.println("Sync EmailProvider.id = " + i);
//        });
    }

    private Flux<UserDto> getAllUsers() {
        return Flux.just(users);
    }

    private Mono<EmailProviderDto> getByUrl(String url) {
        return Flux.just(emailProviders)
                .filter(p ->
                        url.equalsIgnoreCase(p.fqdn))
                .singleOrEmpty();
    }

}
