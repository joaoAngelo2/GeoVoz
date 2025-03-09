package com.cursoandroid.geovoz;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "capitais")

public class CapitalEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nomeCapital;
    public String imagem;

    public CapitalEntity(String nomeCapital, String imagem) {
        this.nomeCapital = nomeCapital;
        this.imagem = imagem;
    }
}
