package com.anurag.ai.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

      @GetMapping("/")
      R m1() {
            return new R();
      }

}
