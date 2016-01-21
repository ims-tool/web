PL/SQL Developer Test script 3.0
245
/* Inserção de anúncio URA */
-- 10/10/2014 - Itamar - Inserção VersionID

/*---------------------------------------*/

declare
  vVersionId      number := :VersionID;
  vName        varchar2(255) := :Name;
  vDescription varchar2(255) := :Description;
  vFlushPrompt number := :FlushPrompt;
  vNextForm    number := :NextForm;
  vTypeAudio   VARCHAR2(5) := :TypeAudio;
  vAudioPath   VARCHAR2(128):= :AudioPath;
  vAudio1      varchar2(255) := :Audio1;
  vAudio2      varchar2(255) := :Audio2;
 vAudio3      varchar2(255) := :Audio3;
  vAudio4      varchar2(255) := :Audio4;
  vAudio5      varchar2(255) := :Audio5;
  vAudio6      varchar2(255) := :Audio6;
  vAudio7      varchar2(255) := :Audio7;
  vAudio8      varchar2(255) := :Audio8;
  vAudio9      varchar2(255) := :Audio9;
  vAudio10      varchar2(255) := :Audio10;
  vAudio11      varchar2(255) := :Audio11;
  vAudio12      varchar2(255) := :Audio12;
  vAudio13      varchar2(255) := :Audio13;
  vAudio14      varchar2(255) := :Audio14;
  vAudio15      varchar2(255) := :Audio15;
  vAudio16      varchar2(255) := :Audio16;
  vAudio17      varchar2(255) := :Audio17;
  vAudio18      varchar2(255) := :Audio18;
  vAudio19      varchar2(255) := :Audio19;
  vAudio20      varchar2(255) := :Audio20;

  vFormId     number;
  vAnnounceId number;
  vPromptId   number;
  vAudioId    number;
begin

  -- Prompt
  select IVR_OWNER.GET_UID into vPromptId from dual;
  insert into IVR_OWNER.prompt (id, name, VERSIONID) values (vPromptId, vName, vVersionId);  

  -- Announce
  select IVR_OWNER.GET_UID into vAnnounceId from dual;
  insert into IVR_OWNER.announce (id, name, description, flushprompt, prompt, nextform, VERSIONID) values (vAnnounceId, substr(vName,0,20), vDescription, vFlushPrompt, vPromptId, -1, vVersionId);
  
  -- Form
  select IVR_OWNER.GET_UID into vFormId from dual;
  insert into IVR_OWNER.form (id, name, description, formtype, formid, VERSIONID) values (vFormId, substr(vName,0,20), vDescription, 1, vAnnounceId, vVersionId);

  -- Audio1
  select IVR_OWNER.GET_UID into vAudioId from dual;
  insert into IVR_OWNER.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio1, vAudio1, vAudioPath, '', vVersionId);
  -- PromptAudio1
  insert into IVR_OWNER.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 1, vVersionId);

  -- Audio2
  if vAudio2 is not null then
    
  vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into IVR_OWNER.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio2, vAudio2, vAudioPath, '', vVersionId);
      -- PromptAudio1
      insert into IVR_OWNER.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 2, vVersionId);
  end if;
  
  
  -- Audio3
  if vAudio3 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio3, vAudio3, vAudioPath, '', vVersionId);
      -- PromptAudio3
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 3, vVersionId);
  end if;
  
  -- Audio4
  if vAudio4 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio4, vAudio4, vAudioPath, '', vVersionId);
      -- PromptAudio4
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 4, vVersionId);
  end if;
  
  -- Audio5
  if vAudio5 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio5, vAudio5, vAudioPath, '', vVersionId);
      -- PromptAudio5
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 5, vVersionId);
  end if;
  
  -- Audio6
  if vAudio6 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio6, vAudio6, vAudioPath, '', vVersionId);
      -- PromptAudio6
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 6, vVersionId);
  end if;
  
  -- Audio7
  if vAudio7 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio7, vAudio7, vAudioPath, '', vVersionId);
      -- PromptAudio7
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 7, vVersionId);
  end if;
  
  -- Audio8
  if vAudio8 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio8, vAudio8, vAudioPath, '', vVersionId);
      -- PromptAudio8
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 8, vVersionId);
  end if;
  
  -- Audio9
  if vAudio9 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio9, vAudio9, vAudioPath, '', vVersionId);
      -- PromptAudio9
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 9, vVersionId);
  end if;
  
  -- Audio10
  if vAudio10 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio10, vAudio10, vAudioPath, '', vVersionId);
      -- PromptAudio10
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 10, vVersionId);
  end if;
  
    -- Audio11
  if vAudio11 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio11, vAudio11, vAudioPath, '', vVersionId);
      -- PromptAudio11
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 11, vVersionId);    
  end if;
  
  -- Audio12
  if vAudio12 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio12, vAudio12, vAudioPath, '', vVersionId);
      -- PromptAudio12
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 12, vVersionId);
  end if;  
  
  -- Audio13
  if vAudio13 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio13, vAudio13, vAudioPath, '', vVersionId);
      -- PromptAudio13
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 13, vVersionId);
  end if;  
  
  -- Audio14
  if vAudio14 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio14, vAudio14, vAudioPath, '', vVersionId);
      -- PromptAudio4
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 14, vVersionId);
  end if;  
  
   -- Audio15
  if vAudio15 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio15, vAudio15, vAudioPath, '', vVersionId);
      -- PromptAudio15
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 15, vVersionId);
  end if; 
  
   -- Audio16
  if vAudio16 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio16, vAudio16, vAudioPath, '', vVersionId);
      -- PromptAudio16
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 16, vVersionId);
  end if; 
 

  -- Audio17
  if vAudio17 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio17, vAudio17, vAudioPath, '', vVersionId);
      -- PromptAudio17
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 17, vVersionId);
  end if;
 

  -- Audio18
  if vAudio18 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio18, vAudio18, vAudioPath, '', vVersionId);
      -- PromptAudio18
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 18, vVersionId);
  end if;
 
  -- Audio19
  if vAudio19 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio19, vAudio19, vAudioPath, '', vVersionId);
      -- PromptAudio19
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 19, vVersionId);
  end if;


  -- Audio20
  if vAudio20 is not null then
    vAudioId:=vAudioId+1;
--      select IVR_OWNER.GET_UID into vAudioId from dual;
      insert into ivr_owner.audio (id, type, name, description, path, property, VERSIONID) values (vAudioId, vTypeAudio, vAudio20, vAudio20, vAudioPath, '', vVersionId);
      -- PromptAudio20
      insert into ivr_owner.promptaudio (prompt, audio, orderNum, VERSIONID) values (vPromptId, vAudioId, 20, vVersionId);
  end if;  
  
  
  
  commit;
  dbms_output.put_line('OK');
  dbms_output.put_line('FormId: '||vFormId);
  dbms_output.put_line('PromptId: '||vPromptId);
  exception
    when others then
      DBMS_OUTPUT.PUT_LINE ('ERROR: '||SQLERRM);
      rollback;
end;
27
VersionID
1
9914551016444
5
Name
1
MsgDtVencNok
5
Description
1
Mensagem de dados de data de venc. não confere
5
FlushPrompt
1
1
3
NextForm
1
-1
5
TypeAudio
1
WAV
5
AudioPath
1
file:///app/audios/qa/3/crm/
5
Audio1
1
Desculpe, os dados não conferem
5
Audio2
0
5
Audio3
0
5
Audio4
0
5
Audio5
0
5
Audio6
0
5
Audio7
0
5
Audio8
0
5
Audio9
0
5
Audio10
0
5
Audio11
0
5
Audio12
0
5
Audio13
0
5
Audio14
0
5
Audio15
0
5
Audio16
0
5
Audio17
0
5
Audio18
0
5
Audio19
0
5
Audio20
0
5
0
