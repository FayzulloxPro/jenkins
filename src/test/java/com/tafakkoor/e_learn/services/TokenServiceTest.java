package com.tafakkoor.e_learn.services;

import com.tafakkoor.e_learn.domain.Token;
import com.tafakkoor.e_learn.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        Token token = new Token();
        
        // Mocking the behavior of the tokenRepository.save method
        when(tokenRepository.save(any(Token.class))).thenReturn(token);

        // Calling the method under test
        tokenService.save(token);

        // Verifying that the save method was called with the expected argument
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void testGenerateToken() {

        String generatedToken = tokenService.generateToken();
        assertNotNull(generatedToken);
    }

}
