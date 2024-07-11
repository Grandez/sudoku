/**
 * Este paquete contiene las utilidades para leer y escribir datos.
 * Dicho de otro modo; de donde obtener el sudoku a jugar y como devolver el sudoku jugado
 *
 * De manera general cada metodo de entrada/salida deberia en su propio paquete
 * Por ejemplo:
 *    consola y/o ficheros: console
 *    Webservice: soap
 *
 * La clase input sera la responsable de obtener la informacion para el juego
 * La clase output la responsable de devolver el resultado
 *
 * Por simplicidad, ambas clases utilizan el DTO GameDefinition
 */
package com.sdp.sudoku.io;