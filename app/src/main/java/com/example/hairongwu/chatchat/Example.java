package com.example.hairongwu.chatchat;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hairongwu on 2/9/16.
 */
public class Example {
        @SerializedName("result_list")
        @Expose
        public List<ResultList> resultList = new ArrayList<ResultList>();
        @SerializedName("result")
        @Expose
        public String result;


        public Example() {
        }
        /**
         *
         * @param resultList
         * @param result
         */

        public Example(List<ResultList> resultList, String result){
                this.result=result;
                this.resultList= resultList;
        }

        public List<ResultList> getResultList(){
                return resultList;
        }
        public void setResultList(List<ResultList> resultList){
                this.resultList=resultList;
        }

        public String getResult(){return result;}
        public void setResult(String result){this.result= result;}
}
