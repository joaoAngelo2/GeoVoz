package com.cursoandroid.geovoz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CapitalDAO {
    @Insert
    void inserirCapital(CapitalEntity capital);

    @Insert // Adicione esta anotação para listas
    void inserirCapitais(List<CapitalEntity> capitais);

    @Query("SELECT * FROM capitais ORDER BY RANDOM() LIMIT 1")
    CapitalEntity getCapitalAleatoria();
}
