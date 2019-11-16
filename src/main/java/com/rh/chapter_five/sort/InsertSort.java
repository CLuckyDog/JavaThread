package com.rh.chapter_five.sort;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/12 11:09
 * @description: 插入排序算法案例代码
 *                  逻辑描述：把已知数组分为两个，一个顺序，一个乱序的，所以，一般把第一个元素作为一个排序过的数组
 *                          然后，从乱序数组中，依次取出一个元素，去顺序数组里面找位置，并插入。
 *                          假设数组长度是5，乱序数组有：4元素,3元素,2元素,1元素
 *                          比对次数1*1+2*1+3*1+4*1=10
 * @modified By:
 */
public class InsertSort {
    public static void insertSort(int[] arr){
        int length=arr.length;
        int j,i,key;/*j,i是循环遍历使用，key临时寄存值*/

        for (i=1;i<length;i++){/*这里从1开始，是因为把第一个元素当做已经排序的数组元素*/
            key=arr[i];/*取出第i个元素值,为他找位置*/
            j=i-1;/*已经排序的数组长度*/
            while(j>=0&&arr[j]>key){/*倒序遍历已排序数组*/
                arr[j+1]=arr[j];/*如果arr[j]大于key，则把该位置空出，留给key，所以，arr[j]向后移动一位*/
                j--;
            }
            arr[j+1]=key;/*把key存入while中空出的位置，实际上是arr[j]的位置，因为while里面j--了*/
        }
    }

    public static void main(String[] args) {
        int[] arr=new int[]{14,2,24,2323,12,3244,12131,4455};
        System.out.println("before------------");
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"---");
        }
        insertSort(arr);
        System.out.println("");
        System.out.println("after------------");
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"---");
        }

    }
}
