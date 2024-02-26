A universal message production service.

What's included ：
1. Cross domain resource sharing permissions have been configured to enable secure data exchange between websites with different domain names.
2. Implemented ConverterFactory to convert database field values into corresponding enumerations.
3. Custom exception handling can distinguish business related exceptions from other exceptions, making it easier to troubleshoot and fix errors.
4. Implement global exception handling logic 
5. Implement serializers and deserializers.

We need a data push record table, and I will name it tdg_data_push_record. We will use a to represent it：
  c_id	c_data_id	c_create_time	c_params_id	c_push_time	c_push_status	c_fail_count	c_data	c_canceled	c_db_status
  bigint	bigint	timestamp	int	timestamp	smallint	int	json	tinyint	tinyint
Among them,a.c_push_status is in push status, 0- pending push, 1- pushed, 2- in progress, and 1- failed push.

We also need a message push parameter table named tdg_data_push_params, which we use b to represent
  c_id	c_topic	c_system_id	c_system_code	c_message_code	c_url	c_debug	c_target_type	c_source_type	c_data_type	c_disable	c_remark
  int	varchar	varchar	varchar	varchar	varchar	tinyint	smallint	int	int	tinyint	varchar

Tips:a.c_params_id  is equal to b.c_id


