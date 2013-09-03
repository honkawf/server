package personinfo;

import java.util.List;

rt java.util.Set;

/**
 * Interface    
 *  *  * or PersonInfoDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

pu
	
	

	 * Perform an initial save of a previously unsaved PersonInfo entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * od use javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 } <pre>
	 . EntityManagerHelper.beginTransaction();
	 * IPersonInfoDAO.save(entity);
	 * EntityManagerHelper.commit();
	 .commit(
	 * 
	 * .commi entity
	 *            PersonInfo entity to persist
	 *  Person RuntimeException
	 *             when the operation failsExcep
		 *             when the operation f

	xce
	 * Delete a persistent PersonInfo entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the store, javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 bject)
	 *  EntityManagerHelper.beginTransaction();
	 * IPersonInfoDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 nagerHel
	 * 
	 * ommit( entity
	 *            PersonInfo entity to delete
	 * 	 * omm RuntimeException
	 *             when the operation failsy to 
	e
	 * 	 *  En RuntimeException
	 *    

	   
	 * Persist a previously saved PersonInfo entity and return it or a copy of
	 * it to the sender. A copy of the PersonInfo entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * to be  javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	  * to be  j EntityManagerHelper.beginTransaction();
	 * entity = IPersonInfoDAO.update(entity);
	 * EntityManagerHelper.commit();
	 ansactio
	 * 
	 * 	 * en entity
	 *            PersonInfo entity to update
	 * 	 * Ent PersonInfo the persisted PersonInfo entity instance, may not be
	 *         the same
	 * PersonI RuntimeException
	 *             if the operation failsnfo the persisted PersonInfo entity instance, may n

	 be
	 *         the same
	  PersonI Ru

	meExceptFind all PersonInfo entities with a specific property value.
	 * 
	 * o enti propertyName
	 *            the name of the PersonInfo property to query
	 * meExce value
	 *            the property value to match
	 * cific property value.
	 * 
	 * he per propertyName
	 *            the name of the PersonInfo property to query
	 * sam v

	ue
	 *            the property value to match
	 * cific properva

	e.
	 * 
	 * 
	publ propertyName
	 *            the n o

	the PersonInfo property to query
	 * ind v

	ue
	 *           e 

	operty value to match
	 * fo entities h 

	specific properva

	e.
	 * 
	 * @param propertyNam *

	          the nam
			e of the PersonInfo proty

	ue

	
	 * @paraal

	
	 *            the property value to c	 

	@return Listrs

	Info> found by query
	 */
	pub L

	er

	nInfo> fin
			dByProperty(String propertyN, 

	ject va
	

		blic List<PersonInfo> findByCustomernambj

	t customerna;	p

	lic List<PersonInfo> findByCardnOb

	ct cardnum
			);

	puc 

	st<PersonInfo>nd

	Bluetooac

	bject bluetooth);

		public Find all PersonInfo entities.
	 * 
	 *  privatekey);

	public List<Pon

	fo> findByIdentificatca

	num(
			Object identificatc	n
);

	public t<

	rsonInfoFind all PersonInfo entities.
	 * 
	 * ublic List<PersonInfo> findByBalance(Object balance);

bl

	 List<PersonInfo> findByPasrd
bject passwo;


	public LFind all PersonInfo entities.
	 * 
	 * ect publickeyn);

	public List<PersonInfo> findByPublickeyd(Object publickeyd);

	publiis
PersonInfo> findByImei(Object imei);

	/**
	 * Find all PersonInfo entities.
	 * 
	 * @return List<PersonInfo> all PersonInfo entities
	 */
	public List<PersonInfo> findAll();
}