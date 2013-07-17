package com.carles.minimal.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath*:META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTest {}
