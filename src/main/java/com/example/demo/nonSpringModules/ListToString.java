package com.example.demo.nonSpringModules;

import java.util.Iterator;
import java.util.List;

public class ListToString {
    public ListToString(){}
    public String listToString(List<Integer>list){
        StringBuilder strbul  = new StringBuilder();
        strbul.append("[");
        Iterator<Integer> iter = list.iterator();
        while(iter.hasNext())
        {
            strbul.append(iter.next());
            if(iter.hasNext()){
                strbul.append(",");
            }
        }
        strbul.append("]");
        System.out.println(strbul.toString());
        return strbul.toString();
    }
}
