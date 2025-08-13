package com.project.repair;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class RepairApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<m;i++){
            list.add(nums1[i]);
        }
        for(int i=0;i<n;i++){
            list.add(nums2[i]);
        }
        Collections.sort(list);
    }

}
