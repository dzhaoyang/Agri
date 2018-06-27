package com.sunsea.parkinghere.biz.service;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IBizService<T> {
    
    public void persist(T a);
    
    public void persistAll(Iterable<T> a);
    
    public void rfdsd(T a);
    
    public void rbi(String a);
    
    public T findById(String a);
    
    public List<T> findAll();
    
    public Page<T> findPage(int a, int b);
    
    public List<T> filterByName(String a);
    
    public Page<T> filterByName(String a, int b, int c);
    
    public Page<T> findPageBy(String a, Object b, int c, int d);
    
    public Page<T> findPageBy(String[] a,
                              Object[] b,
                              int c,
                              int d);
}
