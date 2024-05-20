package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.RegisterNotebook;

public interface IRegisterNotebookService {
     void save(RegisterNotebook registerNotebook);

     RegisterNotebook getRegisterNotebookByClassId(int id);
}
