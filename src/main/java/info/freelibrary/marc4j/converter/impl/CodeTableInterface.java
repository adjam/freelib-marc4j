
package info.freelibrary.marc4j.converter.impl;

public interface CodeTableInterface {

    /**
     * 
     * @param i
     * @param g0
     * @param g1
     * @return
     */
    public boolean isCombining(int i, int g0, int g1);

    /**
     * 
     * @param c
     * @param mode
     * @return
     */
    public char getChar(int c, int mode);

};
