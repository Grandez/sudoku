package com.sdp.sudoku.config;

import java.util.HashSet;
import java.util.Set;

/*
 * Contiene la informacion para ejecutar una jugada:
 * 1. Tipo de Sudoku: Dimension
 * 2. Juego de simbolos
 * 2. Datos de inicio
 */
public class GameDefinition {
    Integer[] data;
    Set<Character> domain = new HashSet<>();
    public int lines = 0;
    public int dim = 3;
    public boolean extraDim = true;
    public int options = 4;
    public GameDefinition () {
        // this.data = data;
    }
    public void      addDomain(char c) { domain.add(c); }
    public Integer[] getData()         { return data;   }
    public int       getDimension()    { return 1;      }
    public boolean   hasSubdimension() {
        return true;
    }
    public byte[] getSymbols() {
        return new byte[0];
    }
    public boolean validate() {
        if (validateSize()) return true;
        if (validateSet())  return true;
        return false;
    }
    private boolean validateSize() {
        boolean rc = false;
        int size = data.length;
        double res = Math.sqrt(size);
        int mod = (int) res;
        if ((res - mod) == 0) {
            dim = mod;
        } else {
            size--;
            res = Math.sqrt(size);
            mod = (int) res;
            if ((res - mod) == 0) {
                extraDim = false;
            } else {
                rc = true;
            }
        }
        return rc;
    }
    private boolean validateSet() {
        int size = dim * dim;
        if (size > domain.size()) return true;
        return false;
    }
}
