package com.example.systemrezerwacji.domain.salonmodule;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ImageRepositoryTestImpl implements ImageRepository{

    private Map<Long, Image> imageDataBase = new ConcurrentHashMap<>();
    private Long id = 1L;

    @Override
    public List<Image> findBySalonId(Long salonId) {
        return imageDataBase.values().stream()
                .filter(image -> image.getSalon().getId().equals(salonId))
                .toList();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Image> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Image> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Image> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Image getOne(Long aLong) {
        return null;
    }

    @Override
    public Image getById(Long aLong) {
        return null;
    }

    @Override
    public Image getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Image> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Image> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Image> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Image> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Image> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Image, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Image> S save(S entity) {
        imageDataBase.put(id, entity);
        id++;
        return entity;
    }

    @Override
    public <S extends Image> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Image> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public List<Image> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Image entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Image> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Image> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return null;
    }
}
