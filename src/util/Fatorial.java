/**
 * 
 */
package util;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */
public class Fatorial {
	public static int fatorial(int value){
		int num, fat = 1;
        num = value;
        for(int i = 1;i <= num; i++){
            fat = fat * i;
        }
        return fat;
	}
}
