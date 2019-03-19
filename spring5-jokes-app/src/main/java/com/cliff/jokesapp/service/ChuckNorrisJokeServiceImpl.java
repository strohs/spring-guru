package com.cliff.jokesapp.service;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

/**
 * Joke Service implementation that gets a random Chuck Norris Joke.
 * uses {@link ChuckNorrisQuotes} to generate the joke
 *
 * @author cliff
 */
@Service
public class ChuckNorrisJokeServiceImpl implements JokeService {

    private final ChuckNorrisQuotes chuckNorrisQuotes;


    public ChuckNorrisJokeServiceImpl() {
        this.chuckNorrisQuotes = new ChuckNorrisQuotes();
    }

    @Override
    public String getJoke() {
        return chuckNorrisQuotes.getRandomQuote();
    }
}
