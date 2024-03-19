package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.IComplaintDao;
import com.demo.dao.IUserDao;
import com.demo.dto.Complaintdto;
import com.demo.model.Complaint;
import com.demo.model.User;

@Service
@Transactional
public class ComplaintService implements IComplaintService{

	@Autowired
	private IComplaintDao complaintDao;
	@Autowired
	private IUserDao udao;
	
	//Get all complaint
	@Override
	public List<?> getAllComplaints() {
		List<Complaint> compList = complaintDao.findAll();
        List<Complaintdto> compls= new ArrayList<Complaintdto>();
        for(Complaint comp: compList) {
        	Complaintdto com = new Complaintdto();
        	com.setComplaintId(comp.getComplaintId());
        	com.setPostedAt(comp.getPostedAt());
        	com.setCategory(comp.getCategory());
        	com.setDescription(comp.getDescription());
        	com.setForGS(comp.getForGS());
        	com.setForAdmin(comp.getForAdmin());
        	com.setStatus(comp.getStatus());
        	com.setState(comp.getState());
        	com.setDistrict(comp.getDistrict());
        	com.setTaluka(comp.getTaluka());
        	com.setVillage(comp.getVillage());
        	com.setRemarks(comp.getRemarks());
        	com.setUserId(comp.getUser().getUserId());
        	compls.add(com);
        }
        return compls;
	}

	//Get Complaint by id
	@Override
	public Complaint getComplaintById(int id) {
		Optional<Complaint> complaint =  complaintDao.findById(id);
		if(complaint!=null)
			return complaint.get();
		return null;
	}

	//Add Complaint
	@Override
	public void addNewComplaint(Complaint comp, int id) {
		User u =udao.findById(id).orElse(null);
		u.setComp(comp);
		udao.save(u);
	}

	//Update Complaint
	@Override
	public void updateComplaint(Complaint comp) {
		Optional<Complaint> complaint =  complaintDao.findById(comp.getComplaintId());
		if(complaint.isPresent()) {
			Complaint cmt = complaint.get();
			cmt.setCategory(comp.getCategory());
			cmt.setDescription(comp.getDescription());
			cmt.setDistrict(comp.getDistrict());
			cmt.setTaluka(comp.getTaluka());
			cmt.setVillage(comp.getVillage());
			complaintDao.save(cmt);
		}
	}

	@Override
	public void deleteComplaint(int id) {
		complaintDao.deleteById(id);
	}

	
	
	// WHERE SELECT * FROM tbl_complaint WHERE status = 'new'
	@Override
	public List<Complaint> getNewComplaints() {
		return complaintDao.getNewComplaints("new");
	}
	

	
	// WHERE SELECT * FROM tbl_complaint WHERE status = 'inprocess'
	@Override
	public List<Complaint> getInProcessComplaints() {
		return complaintDao.getInProcessComplaints("inprocess");
	}
	
	
	// WHERE SELECT * FROM tbl_complaint WHERE status = 'completed'
	@Override
	public List<Complaint> getCompletedComplaints() {
		return complaintDao.getCompletedComplaints("completed");
	}
	

	@Override
	public void editNewStatus(Complaint cmp) {
		Optional<Complaint> complaint =  complaintDao.findById(cmp.getComplaintId());
		if(complaint.isPresent()) {
			Complaint cmt = complaint.get();
			cmt.setStatus("inprocess");
			complaintDao.save(cmt);
		}
	}

	@Override
	public void editInProcessStatus(Complaint cmp) {
		Optional<Complaint> complaint =  complaintDao.findById(cmp.getComplaintId());
		if(complaint.isPresent()) {
			Complaint cmt = complaint.get();
			cmt.setStatus("completed");
			complaintDao.save(cmt);
		}
	}

	// WHERE SELECT * FROM tbl_complaint WHERE village = 'village'
	@Override
	public List<Complaint> getVillageComplaints(String village) {
		return complaintDao.findByVillage(village);
	}

	// WHERE SELECT COUNT(*) FROM tbl_complaint WHERE status = 'completed'
	@Override
	public int getCompletedCount() {
		return complaintDao.getCompletedComplaintsCount("completed");
	}

	// WHERE SELECT COUNT(*) FROM tbl_complaint WHERE status = 'inprocess'
	@Override
	public int getInProcessCount() {
		return complaintDao.getInProcessComplaintsCount("inprocess");
	}

	// WHERE SELECT COUNT(*) FROM tbl_complaint WHERE status = 'new'
	@Override
	public int getNewCount() {
		return complaintDao.getNewComplaintsCount("new");
	}
	

}
