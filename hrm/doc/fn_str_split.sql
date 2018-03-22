-- hrm 处理人处理函数
--创建一个函数 然后对数据进行分割 处理 
create or replace type strsplit_type as table of VARCHAR2(32676);
create or replace function fn_str_split(p_value varchar2,
                                    p_split varchar2 := ',')
 return strsplit_type
  pipelined is
  v_idx       integer;
  v_str       varchar2(500);
  v_strs_last varchar2(4000) := p_value;
begin
  loop
    v_idx := instr(v_strs_last, p_split);
    exit when v_idx = 0;
    v_str       := substr(v_strs_last, 1, v_idx - 1);
    v_strs_last := substr(v_strs_last, v_idx + 1);
    pipe row(v_str);
  end loop;
  pipe row(v_strs_last);
  return;

end fn_str_split; 


   
 
