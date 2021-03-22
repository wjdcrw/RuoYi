package com.ruoyi.busi.service.impl;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusiBookBorrowServiceImpl implements IBusiBookBorrowService {
    @Autowired
    private BusiBookBaseinfoMapper busiBookBaseinfoMapper;
    @Override
    public int preBorrowBookById(Long bookId) {
        //当前用户id
        Long userId = ShiroUtils.getUserId();
        //查询当前用户可预借

        //
        return 0;
    }

    @Override
    public int borrowBookById(Long id) {
        return 0;
    }

    @Override
    public int returnBookById(Long id) {
        return 0;
    }

    @Override
    public boolean canPreBorrow(Long bookId, Long userId) throws Exception{
        return false;
    }

    @Override
    public boolean canBorrow(Long bookId, Long userId) throws Exception{
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(bookId);
        busiBookBaseinfo.getState();
        if(busiBookBaseinfo.getState()!=0){
            throw new Exception("");
        }
        return false;
    }

    /**
     * 查询图书是否被占用(预借或借取)
     * @param bookId
     * @return
     */
    private boolean isFree(Long bookId){
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(bookId);
        busiBookBaseinfo.getState();
        return busiBookBaseinfo.getState()==0;
    }

    /**
     * 查询用户借取数量是否已满
     * @param bookId
     * @return
     */
    private boolean isBorrowFull(Long bookId){
        return false;
    }

    /**
     * 查询用户预借数量是否已满
     * @param bookId
     * @return
     */
    private boolean isPreBorrowFull(Long bookId){
        return false;
    }

}
