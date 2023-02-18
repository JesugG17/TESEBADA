CREATE INDEX indiceestado ON Chequera (estado);
go
create PROCEDURE sp_actualizarSaldo
    @estado VARCHAR(50)
AS
BEGIN
	DECLARE @idChequera varchar(50), @saldo money
	DECLARE cur cursor for select CHEQUERA,SALDO from CHEQUERA where ESTADO = @estado and ESTATUS='A';
	BEGIN TRANSACTION;
	OPEN cur
	FETCH NEXT FROM cur INTO @idChequera, @saldo

	WHILE @@FETCH_STATUS = 0
	BEGIN
		update CHEQUERA set saldo = @saldo + (CAST(RAND() * 9 AS INT)+1)*1000 where CHEQUERA = @idChequera
		FETCH NEXT FROM cur INTO @idChequera, @saldo
	END
	CLOSE cur
	DEALLOCATE cur
	  IF @@ERROR <> 0
	  BEGIN
		ROLLBACK TRANSACTION;
		RETURN;
	  END
	  COMMIT TRANSACTION;
END
go
exec sp_actualizarSaldo'sinaloa';
select * from CHEQUERA where ESTADO ='sinaloa';
update CHEQUERA set SALDO =1000;