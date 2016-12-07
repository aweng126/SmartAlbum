package com.example.kingwen.smartalbum.Utils;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kingwen on 2016/12/7.
 */
public class DataHelperTest extends AndroidTestCase {

    private DataHelper mDataHelper;

    @Before
    public void setUp() throws Exception {
        /**
         * 得到最开始的实例对象mDataHelper
         */
        mDataHelper=new DataHelper(getContext());
    }

    @Test
    public void testGetDataHelperInstance() throws Exception {

        /**
         * 确保通过单例模式会正常得到我们最后的DataHelper对象
         */
        assertTrue(DataHelper.getDataHelperInstance(getContext())!=null);

    }

    @Test
    public void testGetPhotos() throws Exception {

        /**
         * 确保得到的照片的集合不为0
         */
        assertTrue(mDataHelper.getPhotos().size()>0);

    }

    @Test
    public void testGetLongLati() throws Exception {

        /**
         * 确保我们最后得到的结果是两个点的经纬度坐标，预计得到的是两个点
         */
        assertEquals(4,mDataHelper.getLongLati().size(),0);

    }

    @Test
    public void testGetPointBySite() throws Exception {

    }

    @Test
    public void testGetPointByPerson() throws Exception {

    }

}