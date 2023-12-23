package org.example.employejdbc.Models;

import java.util.List;
import java.util.Optional;

public interface CRUD<T,K> {
    public boolean Add(T object) ;
    public List<T> All() ;
    Optional<T> Read(K ID) ;
    boolean Update(T object, K ID) ;
    boolean Delete(K ID) ;
    Long Count() ;
}
