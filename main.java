import java.util.Scanner;

public class main {
    
    
    public static void main(String[] args){
        Scanner leer = new Scanner(System.in);        
        arreglo arreglito = new arreglo();
        System.out.println("\n\nIntrucciones:");
        System.out.println("Cuando deseas destapar una celda, pon 0 al principio, separa con espacio, posteriormente las coordenadas separadas con espacios, ejemplo: '0 1 1'");
        System.out.println("Cuando deseas poner una bandera, pon 1 al principio, separa con espacio, posteriormente las coordenadas separadas con espacios, ejemplo: '1 1 1'");
        System.out.println("Cuando deseas poner una interrogación, pon 2 al principio, separa con espacio, posteriormente las coordenadas separadas con espacios, ejemplo: '2 1 1'");
        System.out.println("Las coordenadas van del 1 al 15");
        boolean bandera = true;
        arreglito.llenar(); //se llenan a 0 las celdas
        arreglito.imprimir_array(); //se imprime el array
        
        int win = 1; //se inicializa la condición de juego
        do{ //se hará un ciclo hasta que se determine la condición de ganar
           win = arreglito.jugar(bandera);  //se llama a jugar, lo que hará que 
           bandera = false; 
           if(win==2){  //si sale de arreglito.jugar un 2, entonces se reiniciará la bandera y la condición 
                bandera = true; 
                win=1;
           }
        }while(win==1);
        
        if(win==3){ //si sale un 3, entonces se ganó el juego
            System.out.println("Has ganado, felicidades =D");
        }else if(win ==0){//si sale 0, entonces se perdió 
            System.out.println("\nHas perdido =(");
        }
    }
}
class arreglo{
    
    cuadro[][] array = new cuadro[15][15]; //se crea un array de datos de tipo cuadro, en donde estará la capa superior e inferior del buscaminas
    int bombas=0;
    
    public void encontrar_b(){ //esta función encuentra las minas, y llama a la función poner números, la cual pone números
        for(int i= 0;i< 15;i++){
            for(int j = 0; j <15; j++){
                if(array[i][j].celda=='B'&&array[i][j].visitado!=true){
                    poner_numero( i, j);
                }
            }
        }
    }
    
    public void poner_numero( int ind_i, int ind_j){ //esta función coloca los números alrededor de las minas, en recorre los alrededores de la mina y si encuentra otro numero, le incrementa un número, 
                                                     //significando que ese esa celda colinda con más de una mina.
        array[ind_i][ind_j].visitado=true;        
        for(int i = ind_i-1; i<ind_i+2;i++){
            for(int j = ind_j-1;j<ind_j+2;j++){
                if(i == -1||i==15||j==-1||j==15){
                    continue;
                }else if(array[i][j].celda=='B'&&array[i][j].visitado!=true){
                    poner_numero( i, j);              
                }
                if(array[i][j].celda=='1'){
                    array[i][j].celda='2';
                }else if (array[i][j].celda=='2'){
                    array[i][j].celda='3';
                }else if (array[i][j].celda=='3'){
                    array[i][j].celda='4';
                }else if (array[i][j].celda=='4'){
                    array[i][j].celda='5';
                }else if (array[i][j].celda=='5'){
                    array[i][j].celda='6';
                }else if(array[i][j].celda!='B'){
                    array[i][j].celda='1';
                }
            }
        }
    }
    
    public void poner_minas(int ind_i, int ind_j){ //Esta función pone minas de manera aleatoria, excepto en los lugares cercanos al punto de inicio y donde ya esté una bomba, entonces se llamará de nuevo
        for(int i = 0; i<30; i++){
            int x = (int) (Math.random() * 14 + 0);
            int y = (int) (Math.random() * 14 + 0);
            
            if(array[x][y].celda == 'B'||(x==ind_i&&y==ind_j)||(limites(ind_i, x, ind_j, y))){
               evitar_rep(ind_i, ind_j); 
            }else{
                array[x][y].celda='B';
                
            }
        }
    }
    
    public boolean limites(int ind_i, int x, int ind_j, int y){//se comprueba que el punto dado no esté en las zonas colindantes a la celda dada
        for(int i = ind_i-2; i<ind_i+3;i++){
            for(int j = ind_j-2;j<ind_j+3;j++){
                if(i==x&&j==y) return true;
            }
        }
        return false;
    }
    
    public int jugar(boolean bandera){ //es la función principal, donde se piden las coordenadas y se comprueba si es mina o número
        Scanner leer = new Scanner(System.in);
        
        System.out.println("\nComandos:");
        System.out.println("Destapar = 0, ejemplo: '0 1 1'");
        System.out.println("Bandera = 1, ejemplo: '1 1 1'");
        System.out.println("Interrogación = 2, ejemplo: '2 1 1'");
        System.out.println("Nota: Las coordenadas empiezan desde 1 hasta 15");
        System.out.println("\tBombas="+(30-bombas));
        int s = leer.nextInt();
        int i = leer.nextInt()-1;
        int j = leer.nextInt()-1;
        if(s==1&&bandera==true){//con esto se pone una bandera
            if(array[i][j].celda_ad=='F'){
                array[i][j].celda_ad=0;
                imprimir_array();
                bombas--;
                return 2;
            }else if(array[i][j].celda_ad=='1'||array[i][j].celda_ad=='2'||array[i][j].celda_ad=='3'||array[i][j].celda_ad=='4'||array[i][j].celda_ad=='5'||array[i][j].celda_ad=='6'){
                System.out.println("No puedes poner bandera aquí");//comprueba que no sea número
                return 1;
            }
            array[i][j].celda_ad='F';   //pone una F como bandera
            bombas++;
            imprimir_array();
            return 2;
        }else if(s==2&&bandera==true){  //esta selección es para poner una interrogación
            if(array[i][j].celda_ad=='?'){
                array[i][j].celda_ad=0;
                
                imprimir_array();
                return 2;
            }else if(array[i][j].celda_ad=='1'||array[i][j].celda_ad=='2'||array[i][j].celda_ad=='3'||array[i][j].celda_ad=='4'||array[i][j].celda_ad=='5'||array[i][j].celda_ad=='6'){
                System.out.println("No puedes poner signo aquí");
                return 1;
            }
            array[i][j].celda_ad='?';
            imprimir_array();
            
            return 2;
        }else if(s==1){
            if(array[i][j].celda_ad=='F'){
                array[i][j].celda_ad=0;
                imprimir_array();
                bombas--;
                return 1;
            }else if(array[i][j].celda_ad=='1'||array[i][j].celda_ad=='2'||array[i][j].celda_ad=='3'||array[i][j].celda_ad=='4'||array[i][j].celda_ad=='5'||array[i][j].celda_ad=='6'){
                System.out.println("No puedes poner bandera aquí");
                return 1;
            }
            array[i][j].celda_ad='F';
            imprimir_array();
            bombas++;
            return 1;
        }else if(s==2){
            if(array[i][j].celda_ad=='?'){
                array[i][j].celda_ad=0;
                imprimir_array();
                
                return 1;
            }else if(array[i][j].celda_ad=='1'||array[i][j].celda_ad=='2'||array[i][j].celda_ad=='3'||array[i][j].celda_ad=='4'||array[i][j].celda_ad=='5'||array[i][j].celda_ad=='6'){
                System.out.println("No puedes poner bandera aquí");
                return 1;
            }
            array[i][j].celda_ad='?';
            
            imprimir_array();
            return 1;
        }else{
            if(bandera == true){ //en caso de que la bandera sea true, es decir, que sea la primer tirada, se pondrán minas dependiendo del punto dado
                poner_minas(i, j);
                encontrar_b();
            }
            if(i <= -1||i>=15||j<=-1||j>=15){ //si introduce coordenadas incorrectas, no hará nada y le pedíra un nuevo tiro
                System.out.println("Coordenadas incorrectas");
                return 1;
            }else if(array[i][j].celda=='B'){   //en caso se que sea una bomba, explotarán todas y terminará el juego
                explotar();
                return 0;
            }else if(array[i][j].celda=='0'){//en caso de que sea 0, se abrirá la isla de 0's y uno
                abrir_isla(i, j);
                imprimir_array();   
            }else{  //en otro caso se abrira la celda que fue seleccionada.
                array[i][j].celda_ad=array[i][j].celda;
                imprimir_array();
            }
        }
            
            if(contar()==195){//en caso de que las celdas abiertas sean 195, entonces el jugador ha ganado
                System.out.println("Has ganado!!");
                return 3;
            }
            return 1;
    }
    
    public void abrir_isla(int ind_i, int ind_j){//esta función abre la isla seleccionada
        array[ind_i][ind_j].visitado=true;
        if(array[ind_i][ind_j].celda_ad=='F'||array[ind_i][ind_j].celda_ad=='?'){   //si es una bandera o un interrogación, entonces retorna
           return; 
        }else{
            array[ind_i][ind_j].celda_ad=array[ind_i][ind_j].celda;//en otro caso, se pone en la capa exterior lo que hay en la interior
        }
        
        if(array[ind_i][ind_j].celda!='0'){ //en caso de que la celda no sea 0, entonces será 0
            return;
         }
        
        for(int i = ind_i-1; i<ind_i+2;i++){    //recorre los alrededores de la celda en busca de otro espacio para abrir
            for(int j = ind_j-1;j<ind_j+2;j++){
                if(i == -1||i==15||j==-1||j==15){ //en caso de que esté fuera del límite, no hará nada
                }else if(array[i][j].celda!='B'&&array[i][j].visitado != true&&no_esquinas(ind_i, ind_j, i , j)){   //en otro caso eso se volverán a llamar a abrir islas
                    abrir_isla(i, j);
                }
            }
        }
    }
    
    public int contar(){    //esta función cuenta las celdas abiertas, para llegar a un resultado
        int contador=0;
        for(int i = 0; i<15;i++){           
            for(int j = 0; j < 15; j++){
                if(array[i][j].celda_ad!=0&&array[i][j].celda_ad!='F'&&array[i][j].celda_ad!='?'){
                    contador++;
                }
            }    
        }
        contador =contador + bombas;
        return contador;
    }
    
    public boolean no_esquinas(int ind_i, int ind_j, int i, int j){
        return (((i!=ind_i-1)&&(j!=ind_j-1))||((i!=ind_i-1)&&(j!=ind_j+1))||((i!=ind_i+1)&&(j!=ind_j-1))||((i!=ind_i+1)&&(j!=ind_j+1)));
    }
    
    public void explotar(){//esta función imprime todo
        for(int i = 0; i<15;i++){
            System.out.print("\n");
            for(int j = 0; j < 15; j++){
                if(i==0&&j==0){
                    System.out.println("  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5");
                }
                 if(j==0&&(i+1)>=10){
                    System.out.print((i+1-10)+" "+array[i][j].celda+" ");
                }else if(j==0){
                    System.out.print((i+1)+" "+array[i][j].celda+" ");
                }else{
                    System.out.print(array[i][j].celda+" ");
                }
            }
        }
    }
    
    public void llenar(){   //esta función llena todo de 0
        for(int i = 0; i<15;i++){           
            for(int j = 0; j < 15; j++){
                cuadro nuevo = new cuadro();
                nuevo.celda_ad=0;
                nuevo.celda='0';
                nuevo.visitado=false;
                array[i][j]=nuevo;
            }
        }
    }
    
   public void evitar_rep(int ind_i, int ind_j){//esta función evita que se pongan bombas en lugares repetidos o cerca del punto de inicio
        int x = (int) (Math.random() * 14 + 0);
        int y = (int) (Math.random() * 14 + 0);
        
        if(array[x][y].celda == 'B'||(x==ind_i&&y==ind_j)||(limites(ind_i, x, ind_j, y))){
            evitar_rep(ind_i, ind_j);            
        }else{
            array[x][y].celda='B';
        }
    }
    
    public void imprimir_array(){//imprime el array
        for(int i = 0; i<15;i++){
            System.out.print("\n");
            for(int j = 0; j < 15; j++){
                if(i==0&&j==0){
                    System.out.println("                     1 1 1 1 1 1");
                    System.out.println("   1 2 3 4 5 6 7 8 9 0 1 2 3 4 5");
                }
                 if(j==0&&(i+1)>=10){
                    System.out.print((i+1)+" "+array[i][j].celda_ad+" ");
                }else if(j==0){
                    System.out.print((i+1)+"  "+array[i][j].celda_ad+" ");
                }else{
                    System.out.print(array[i][j].celda_ad+" ");
                }
            }
        }
    }
}

class cuadro{//la clase cuadro, que representa una celda
        char celda;
        char celda_ad;
        boolean visitado;
}
