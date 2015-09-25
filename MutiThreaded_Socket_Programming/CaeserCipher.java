/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * INSTRUCTION: CAESER CIPHER IS WORKING BASED ON ASCII VALUES
 * FOR ENCRYPTION : ADD 3 TO EACH CHARACTER ASCII VALEUS
 * FOR DECRYPTION : SUBTRACT 3 FROM EACH CHARACTER ASCII VALUES
 * ASCII VALUES FOR [A-Z][a - z] IS DIFFERENT HELLO & hello WILL ENCRYPTED AND DECRYPTED IN DIFFERENT MANNER    
 * 
 */
public class CaeserCipher 
{
    // ENCRYPTION BASED ON CASER CIPHER
    public String encrypt(String exp)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < exp.length() ; i++)
        {
            char c = exp.charAt(i);
            c = (char)((c + 3));
            sb.append(c);
        }
        return sb.toString();
    }
    // DECRYPTION BASED ON CASER CIPHER
    public String decrypt(String exp)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < exp.length() ; i++)
        {
            char c = exp.charAt(i);
            c = (char)((c - 3));
            sb.append(c);
        }
        return sb.toString();
    }
    
}
