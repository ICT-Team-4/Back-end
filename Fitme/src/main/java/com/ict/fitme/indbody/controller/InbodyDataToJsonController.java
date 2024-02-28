package com.ict.fitme.indbody.controller;

import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ict.fitme.inbody.InbodyDataDao;

@RestController
public class InbodyDataToJsonController {

    private final InbodyDataDao inbodyDataDao;

    public InbodyDataToJsonController(InbodyDataDao inbodyDataDao) {
        this.inbodyDataDao = inbodyDataDao;
    }

    @GetMapping("/inbody")
    public ResponseEntity<String> getInbodyData() {
        try {
            JSONArray data = inbodyDataDao.getInbodyData();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/inbody/{accountNo}")
    public ResponseEntity<String> getInbodyDataByAccountNo(@PathVariable String accountNo) {
        try {
            JSONArray data = inbodyDataDao.getInbodyDataByAccountNo(accountNo);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
}
