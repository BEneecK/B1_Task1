set @rowindex := -1;
set @rowid=0;
set @cnt=(select count(*) from task1_db.table);
set @middle_no=ceil(@cnt/2);
set @odd_even=null;
select AVG(doubleNumb) AS median from (select doubleNumb,@rowid:=@rowid+1 as rid, 
(CASE WHEN(mod(@cnt,2)=0)
THEN @odd_even:=1 ELSE @odd_even:=0 END) as odd_even_status  from task1_db.table  order by doubleNumb)
as tbl where tbl.rid=@middle_no or tbl.rid=(@middle_no+@odd_even);