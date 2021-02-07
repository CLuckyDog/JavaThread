package com.rh.design_thread.chapter_five.sort;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/12 15:25
 * @description: 快速排序案例代码，面试最多的排序方法
 *                  https://blog.csdn.net/boy_chen93/article/details/85049274
 * @modified By:
 */
public class QuickSort {
    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        /*temp就是基准位,这里选第一个数为基准位*/
        temp = arr[low];

        while (i<j) {
            /*先看右边，依次往左递减*/
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            /*再看左边，依次往右递增*/
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            /*如果满足条件则交换*/
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        /*最后将基准位与i和j相等位置的数字交换*/
        arr[low] = arr[i];
        arr[i] = temp;
        /*递归调用左半数组*/
        quickSort(arr, low, j-1);
        /*递归调用右半数组*/
        quickSort(arr, j+1, high);
    }


    public static void main(String[] args){
        int[] arr = {7,10,2,4,7,1,8,5,19};
        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }
}
