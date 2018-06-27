package com.sunsea.parkinghere.biz.repository.custom;



public interface BookingBillRepositoryCustom extends
                                               AbstractRepositoryCustom {
    
   // public Iterator<PrivateBitsBillGroup> groupPrivateBitsBill(Parking parking,String date);
    
    //public List<BookingBill> findByParkingAndDateRange(Parking parking,Date from, Date to);
	
    public long count(String time);
}
