package com.ruoyi.busi.service;

public interface IBusiBookBorrowService {
    /**
     * 图书预借
     * @param id
     * @return
     */
    public int preBorrowBookById(Long bookId);

    /**
     * 借书
     * @param id
     * @return
     */
    public int borrowBookById(Long bookId);

    /**
     * 还书
     * @param id
     * @return
     */
    public int returnBookById(Long bookId);

    /**
     * 是否可预借
     * @param bookId
     * @param userId
     * @return
     */
    public boolean canPreBorrow(Long bookId,Long userId) throws Exception;

    /**
     * 是否可借
     * @param bookId
     * @param userId
     * @return
     */
    public boolean canBorrow(Long bookId,Long userId) throws Exception;
}
