package com.example.md5ss1csdlorm.model.repository;

import com.example.md5ss1csdlorm.model.entity.Music;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MusicRepository implements IMusicRepository{
    //Đây là biến dùng để quản lý phiên (session) của Hibernate.
    //SessionFactory là một đối tượng quản lý phiên, cho phép bạn tương tác với cơ sở dữ liệu thông qua Hibernate.
    // Biến này được khởi tạo một lần duy nhất và sau đó được sử dụng để tạo các phiên khi cần thiết.
    private static SessionFactory sessionFactory;
    // Đây là biến dùng để quản lý thực thể (entity) của JPA.
    // EntityManager là một giao diện trong JPA, cung cấp các phương thức để tương tác với cơ sở dữ liệu.
    // Tương tự như SessionFactory, biến này cũng được khởi tạo một lần duy nhất và sau đó được sử dụng để thao tác với thực thể.
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.config.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    @Override
    //phương thức được định nghĩa để trả về danh sách các đối tượng Person.
    public List<Music> findAll() {
        // Tạo một danh sách rỗng để lưu trữ danh sách các đối tượng Person được truy vấn từ cơ sở dữ liệu.
        List<Music> musicList = new ArrayList<>();
        // HQL (Hibernate Query Language) truy vấn để lấy tất cả các đối tượng Person từ cơ sở dữ liệu. Trong HQL, "Person" là tên của lớp đối tượng trong Java, và "p" là một biến đại diện cho đối tượng Person trong truy vấn.
        String hql= "SELECT m FROM Music AS m";
        //Tạo một TypedQuery để thực hiện truy vấn HQL. entityManager là một đối tượng quản lý thực thể trong JPA.
        TypedQuery<Music> type = entityManager.createQuery(hql, Music.class);
        //  Thực hiện truy vấn và lấy về danh sách các đối tượng Person từ cơ sở dữ liệu.
        musicList = type.getResultList();
        //Trả về danh sách các đối tượng Music đã truy vấn.
        return musicList;
    }

    @Override
    //Phương thức được định nghĩa để tìm kiếm và trả về một đối tượng Person dựa trên ID.
    public Music findById(Long id) {
    //Tạo một TypedQuery để thực hiện truy vấn HQL. Trong truy vấn này, bạn sử dụng điều kiện WHERE p.id=:id để chỉ định rằng bạn muốn lấy đối tượng Person có ID tương ứng với giá trị của tham số id.
        TypedQuery<Music> type = entityManager.createQuery("SELECT m FROM Music AS m WHERE m.id=:id", Music.class);
    //Đặt giá trị của tham số id cho truy vấn. Điều này sẽ thay thế :id trong truy vấn bằng giá trị thực tế của tham số.
        type.setParameter("id",id);
    //Thực hiện truy vấn và trả về kết quả duy nhất dưới dạng một đối tượng Person.
    //Lưu ý rằng, nếu không tìm thấy kết quả hoặc tìm thấy nhiều hơn một kết quả, phương thức này sẽ ném ra các ngoại lệ tương ứng.
        Music m = type.getSingleResult();
        return m;
    }

    @Override
    public void save(Music m) {
        // Khởi tạo các đối tượng để quản lý giao dịch
        Session session = null;
        Transaction transaction = null;
        try {
            // Khởi tạo session (phiên) để tương tác với cơ sở dữ liệu
            session = sessionFactory.openSession();
            // Bắt đầu một giao dịch
            transaction = session.beginTransaction();

            if (m.getId() == null) {
                // Nếu đối tượng Person chưa có ID, thực hiện thêm mới
                session.save(m);
            } else {
                // Nếu đối tượng Person đã có ID, thực hiện cập nhật

                // Lấy thông tin đối tượng cũ cần cập nhật ra
                Music old = findById(m.getId());

                if (m.getLink() == null) {
                    // Nếu trường link của đối tượng mới là null, giữ nguyên giá trị link cũ
                    m.setLink(old.getLink());
                }

                // Cập nhật thông tin từ đối tượng mới (p) lên đối tượng cũ (old)
                old.copy(m);

                // Lưu hoặc cập nhật đối tượng cũ đã được cập nhật thông tin
                session.saveOrUpdate(old);
            }

            // Kết thúc giao dịch bằng việc lưu các thay đổi vào cơ sở dữ liệu
            transaction.commit();
        } catch (Exception e) {
            // In ra thông tin lỗi nếu có
            e.printStackTrace();
            if (transaction != null) {
                // Nếu giao dịch vẫn còn hoạt động, hủy bỏ giao dịch
                transaction.rollback();
            }
        } finally {
            // Đảm bảo đóng session sau khi hoàn thành giao dịch
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Session session =null;
        Transaction transaction = null;
        try{
            // Khởi tạo session (phiên) để tương tác với cơ sở dữ liệu
            session = sessionFactory.openSession();
            // Bắt đầu một giao dịch
            transaction = session.beginTransaction();
            // Xóa đối tượng bài hát dựa trên ID
            session.delete(findById(id));
            // Kết thúc giao dịch, thực hiện xóa trong cơ sở dữ liệu
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction!=null){
                // Hủy bỏ giao dịch nếu có lỗi
                transaction.isActive();
            }
        }finally {
            if (session!=null){
                // Đảm bảo đóng phiên sau khi hoàn thành giao dịch
                session.close();
            }
        }
    }
}
