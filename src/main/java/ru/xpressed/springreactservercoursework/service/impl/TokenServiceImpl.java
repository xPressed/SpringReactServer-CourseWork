package ru.xpressed.springreactservercoursework.service.impl;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import ru.xpressed.springreactservercoursework.repository.UserRepository;
import ru.xpressed.springreactservercoursework.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;

    @Override
    public String generateNewToken() {
        boolean isGenerated = false;
        String generated = null;
        while (!isGenerated) {
            generated = RandomString.make(32);
            if (!userRepository.existsUserByToken(generated)) {
                isGenerated = true;
            }
        }
        return generated;
    }
}
