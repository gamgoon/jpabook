package xyz.gamgoon.jpabook.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.clear();
		}

		emf.close();
	}

	private static void logic(EntityManager em) {

		String id = "id20";
		Member member = new Member();
		member.setId(id);
		member.setUsername("윤욱");
		member.setAge(3);

		// 등록
		em.persist(member);

		// 수정
		member.setAge(20);

		// 한 건 조회
		Member findMember = em.find(Member.class, id);
		System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

		// 목록 조회
		List<Member> members =
				em.createQuery("select m from Member m", Member.class)
				.getResultList();
		System.out.println("members.size=" + members.size());

		// 삭제
		em.remove(member);
	}
}
