package com.sunsea.parkinghere.biz.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sunsea.parkinghere.biz.repository.AbstractRepository;
import com.sunsea.parkinghere.biz.service.IBizService;

public abstract class AbstractServiceImpl<T> implements IBizService<T> {
    
    protected abstract AbstractRepository<T> getRepository();
    
    public void persist(T entity) {
        getRepository().save(entity);
    }
    
    public void persistAll(Iterable<T> entities) {
        getRepository().save(entities);
    }
    
    public void rfdsd(T entity) {
        getRepository().delete(entity);
    }
    
    public void rbi(String id) {
        getRepository().delete(id);
    }
    
    public T findById(String id) {
        return getRepository().findOne(id);
    }
    
    public List<T> findAll() {
        return (List<T>) getRepository().findAll();
    }
    
    public Page<T> findPage(int start, int limit) {
        PageRequest pageable = new PageRequest(start, limit);
        return getRepository().findAll(pageable);
    }
    
    public List<T> filterByName(String name) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Page<T> filterByName(String name, int start, int limit) {
        throw new UnsupportedOperationException();
    }
    
    public Page<T> findPageBy(String name, Object value, int start, int limit) {
        throw new UnsupportedOperationException();
    }
    
    public Page<T> findPageBy(String[] names,
                              Object[] values,
                              int start,
                              int limit) {
        throw new UnsupportedOperationException();
    }
}
