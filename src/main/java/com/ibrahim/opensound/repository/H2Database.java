package com.ibrahim.opensound.repository;

import com.ibrahim.opensound.models.Sound;
import org.springframework.data.repository.CrudRepository;

public interface H2Database extends CrudRepository<Sound, Long> {
}
