package com.rh.design_thread.chapter_five.sort;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/12 10:21
 * @description: 冒泡排序案例代码
 *                  逻辑描述：就是依次对数组进行排序，从全数组，依次减一。
 *                  假设数组是5个长度，那么就要有5,4,3,2四个数组排序。
 *                  每个数组的排序比对次数是4,3,2,1。
 * @modified By:
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr){
        /*
        * 注意这两个for循环，外层是倒叙，内层以外层为上限循环。
        * */
        for (int i=arr.length-1;i>0;i--){
            for (int j=0;j<i;j++){
                if(arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr=new int[]{14,2,24,2323,12,3244,12131,4455};
        System.out.println("before------------");
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"---");
        }
        bubbleSort(arr);
        System.out.println("");
        System.out.println("after------------");
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"---");
        }

    }
}
