package app.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class Page implements Serializable {
    //Конечная строка в базах данных
    private Long endRow=-1l;
    private boolean isEnd=false;
    //Число строк в одной странице
    private int limit=100;
    //Начальная строка в странице
    private int startRow=1;
    public Page(){
    }
    public Page(int lim){
        limit=lim;
    }
    public void next(){
        if (endRow!=-1)
        {
            if (endRow>startRow+limit)  startRow=startRow+limit+1;
            else isEnd=true;
        }else startRow=startRow+limit+1;
    }
    public void prev(){
        if (startRow-limit>=0) {startRow-=(limit-1);isEnd=false; } else {isEnd=true; startRow=0;}
    }
    public Integer getEndofPage(){
        return startRow+limit;
    }

}
