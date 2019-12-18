package spring17.model;

import lombok.Data;

@Data
public class Notification {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.id
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.notifier
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Long notifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.receiver
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Long receiver;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.outerId
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Long outerid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.type
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.gmt_create
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Long gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notification.status
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.id
     *
     * @return the value of notification.id
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.id
     *
     * @param id the value for notification.id
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.notifier
     *
     * @return the value of notification.notifier
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Long getNotifier() {
        return notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.notifier
     *
     * @param notifier the value for notification.notifier
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.receiver
     *
     * @return the value of notification.receiver
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.receiver
     *
     * @param receiver the value for notification.receiver
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.outerId
     *
     * @return the value of notification.outerId
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Long getOuterid() {
        return outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.outerId
     *
     * @param outerid the value for notification.outerId
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setOuterid(Long outerid) {
        this.outerid = outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.type
     *
     * @return the value of notification.type
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.type
     *
     * @param type the value for notification.type
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.gmt_create
     *
     * @return the value of notification.gmt_create
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.gmt_create
     *
     * @param gmtCreate the value for notification.gmt_create
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notification.status
     *
     * @return the value of notification.status
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notification.status
     *
     * @param status the value for notification.status
     *
     * @mbggenerated Wed Dec 18 22:34:49 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}