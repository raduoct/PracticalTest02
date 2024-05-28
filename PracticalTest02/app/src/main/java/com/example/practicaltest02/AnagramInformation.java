package com.example.practicaltest02;


import androidx.annotation.NonNull;

import java.util.List;

public class AnagramInformation {

    private final List<String> anagrams;

    public AnagramInformation(List<String> anagrams) {
        this.anagrams = anagrams;
    }

    public List<String> getAnagrams() {
        return anagrams;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnagramInformation{" +
                "anagrams=" + anagrams +
                '}';
    }

}