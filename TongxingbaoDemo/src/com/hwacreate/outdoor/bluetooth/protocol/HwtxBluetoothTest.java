package com.hwacreate.outdoor.bluetooth.protocol;

public class HwtxBluetoothTest {

	public HwtxBluetoothTest() {
	}

	public static void main(String[] args) {
		// 查询模式
		System.out.println("-------查询模式-------");
		HwtxCommandQueryMode hwtxCommandQueryMode = new HwtxCommandQueryMode();
		// System.out.println(hwtxCommandQueryMode.bytesToHexText());
		//
		// System.out.println(hwtxCommandQueryMode.toBytes());
		// hwtxCommandQueryMode.fromBytes("0xB2 0xCA 0x3D 0x32 0x00".getBytes());
		System.out.println(hwtxCommandQueryMode.bytesToHexText());
		System.out.println("-------设置领队-------");
		HwtxCommandLeaderSetting command1 = new HwtxCommandLeaderSetting();
		command1.enableLeader();
		System.out.println(command1.bytesToHexText());
		System.out.println("-------取消领队-------");
		command1.disableLeader();
		System.out.println(command1.bytesToHexText());
		System.out.println("Tag: "
				+ String.format("0x%X", command1.getCommandTagInteger()));
		System.out.println("Flag: " + command1.getCommandFlagString());
		System.out.println("Type: " + command1.getCommandTypeInteger());

		byte[] bytes = command1.toBytes();

		System.out.println("--------");

		// 测试还原命令内容
		HwtxCommandLeaderSetting command2 = new HwtxCommandLeaderSetting();
		command2.fromBytes(bytes);
		System.out.println(command2.bytesToHexText());
		System.out.println("Tag: "
				+ String.format("0x%X", command2.getCommandTagInteger()));
		System.out.println("Flag: " + command2.getCommandFlagString());
		System.out.println("Type: " + command2.getCommandTypeInteger());
		System.out.println("----设备参数表----");
		HwtxDataFirmwareConfig data1 = new HwtxDataFirmwareConfig();

		data1.setGpsIntervalInteger(0x6);
		data1.setHktAtIntervalInteger(0x20);
		data1.setLostContactNumInteger(0x1A);
		data1.setWarningDistance1Integer(0x11);
		data1.setWarningDistance2Integer(0x12);
		data1.setWarningDistance3Integer(0x13);
		data1.setWarningBatteryPercentInteger(0x36);
		data1.setConfigCRCInteger(0x87654321);
		System.out.println(data1.bytesToHexText());
		System.out.println("----队员信息表----");// TODO
		// START需要同步的队员信息表------------------------------------------------------
		HwtxDataMemberDeviceInfo deviceInfo1 = new HwtxDataMemberDeviceInfo();
		HwtxDataMemberInfo memberInfo1 = new HwtxDataMemberInfo();

		try {
			deviceInfo1.setDevNumInGroup(31);
			deviceInfo1.setDeviceIsLdb(true);
			deviceInfo1.setDeviceReady(true);
			memberInfo1.setDevInfo(deviceInfo1);
			memberInfo1.setDeviceSnString("123456");
			System.out.println("1 DevNumInGroup: "
					+ memberInfo1.getDevInfo().getDevNumInGroup());
			System.out.println("1 DeviceIsLDB: "
					+ (memberInfo1.getDevInfo().getDeviceIsLdb() ? "是" : "否"));
			System.out.println("1 DeviceReady: "
					+ (memberInfo1.getDevInfo().getDeviceReady() ? "是" : "否"));
			System.out
					.println("1 DeviceSn: " + memberInfo1.getDeviceSnString());
			System.out.println(Integer.toBinaryString(HwtxCommandUtility
					.bytesToInt32(memberInfo1.getDevInfo().toBytes())));
			System.out.println(memberInfo1.bytesToHexText());

			HwtxDataMemberInfo memberInfo2 = new HwtxDataMemberInfo();
			memberInfo2.fromBytes(memberInfo1.toBytes());
			System.out.println("2 DevNumInGroup: "
					+ memberInfo2.getDevInfo().getDevNumInGroup());
			System.out.println("2 DeviceIsLDB: "
					+ (memberInfo2.getDevInfo().getDeviceIsLdb() ? "是" : "否"));
			System.out.println("2 DeviceReady: "
					+ (memberInfo2.getDevInfo().getDeviceReady() ? "是" : "否"));
			System.out
					.println("2 DeviceSn: " + memberInfo2.getDeviceSnString());
			System.out.println(Integer.toBinaryString(HwtxCommandUtility
					.bytesToInt32(memberInfo2.getDevInfo().toBytes())));
			System.out.println(memberInfo2.bytesToHexText());
		} catch (HwtxCommandException e) {
			e.printStackTrace();
		}
		System.out.println("-------- HwtxDataMemberInfoApp");
		HwtxDataMemberInfoApp memberInfoApp = new HwtxDataMemberInfoApp();
		memberInfoApp.setIsAttend(true);
		memberInfoApp.setIsUseAP(true);
		System.out.println(memberInfoApp.bytesToHexText());

		System.out.println("-------- HwtxDataMemberInfoBtApp");
		HwtxDataMemberInfoBtApp memberInfoBtApp = new HwtxDataMemberInfoBtApp();
		memberInfoBtApp.setMemberInfo(memberInfo1);
		memberInfoBtApp.setNickNameString("我的新昵称");
		memberInfoBtApp.setMemberInfoApp(memberInfoApp);
		System.out.println(memberInfoBtApp.bytesToHexText());
		System.out.println("DeviceSn: " + memberInfoBtApp.getNickNameString());
		System.out.println("-------- 同步队员信息（1126）  HwtxDataGroupInfoAllBtApp");
		HwtxDataGroupInfoAllBtApp groupInfoAllBtApp1 = new HwtxDataGroupInfoAllBtApp();
		groupInfoAllBtApp1.setCrcGrpInfoAllBtAppShort((short) 0x1234);
		groupInfoAllBtApp1.setSizeInByteShort((short) 0x2234);
		groupInfoAllBtApp1.setRtcTimeInteger(0x32345678);
		groupInfoAllBtApp1.setGrpCountDeviceByte((byte) 0x42);
		groupInfoAllBtApp1.setGrpCountAttendByte((byte) 0x52);
		groupInfoAllBtApp1.setGrpCountTotalByte((byte) 0x62);
		groupInfoAllBtApp1.addIndexMemberInfoArray(memberInfoBtApp);
		System.out.println(groupInfoAllBtApp1.bytesToHexText());
		// END
		// 需要同步的队员信息表------------------------------------------------------下面这个方法时打印出以上数据
		HwtxDataGroupInfoAllBtApp groupInfoAllBtApp2 = new HwtxDataGroupInfoAllBtApp();
		groupInfoAllBtApp2.fromBytes(groupInfoAllBtApp1.toBytes());
		System.out.printf("CrcGrpInfoAllBtApp: 0x%X\r\n",
				groupInfoAllBtApp2.getCrcGrpInfoAllBtAppShort());
		System.out.printf("SizeInByte: 0x%X\r\n",
				groupInfoAllBtApp2.getSizeInByteShort());
		System.out.printf("RtcTime: 0x%X\r\n",
				groupInfoAllBtApp2.getRtcTimeInteger());
		System.out.printf("GrpCountDevice: 0x%X\r\n",
				groupInfoAllBtApp2.getGrpCountDeviceByte());
		System.out.printf("GrpCountAttend: 0x%X\r\n",
				groupInfoAllBtApp2.getGrpCountAttendByte());
		System.out.printf("GrpCountTotal: 0x%X\r\n",
				groupInfoAllBtApp2.getGrpCountTotalByte());
		for (int i = 0; i < groupInfoAllBtApp2.getIndexMemberInfoArray().size(); ++i) {
			System.out.println(groupInfoAllBtApp2.getIndexMemberInfoArray()
					.get(i).bytesToHexText());
		}
		// END 需要同步的队员信息表------------------------------------------------------
		// TODO
		// 添加入组------------------------------------------------------
		System.out.println("-----将设备号为XXXXXX的同行宝添加入组---");
		try {
			HwtxCommandAddDevice addDevice = new HwtxCommandAddDevice();
			addDevice.setDeviceSn("123456");
			System.out.println(addDevice.bytesToHexText());
			System.out.println("DeviceSn: " + addDevice.getDeviceSn());
		} catch (HwtxCommandException e) {
			e.printStackTrace();
		}

		System.out.println("-------- HwtxDataMemberInfoMap");
		HwtxDataMemberInfoMap memberInfoMap = new HwtxDataMemberInfoMap();
		memberInfoMap.setAtMappingAddrByte((byte) 0x65);
		memberInfoMap.setMemberInfo(memberInfo1);
		System.out.println(memberInfoMap.bytesToHexText());

		System.out.println("-------- HwtxDataGroupInfoAll");
		HwtxDataGroupInfoAll groupInfoAll1 = new HwtxDataGroupInfoAll();
		groupInfoAll1.setCrcGrpInfoAllBtAppShort((short) 0x1234);
		groupInfoAll1.setSizeInByteShort((short) 0x2234);
		groupInfoAll1.setRtcTimeInteger(0x32345678);
		groupInfoAll1.setGroupIDShort((short) 0x1F34);
		groupInfoAll1.setGrpCountDeviceByte((byte) 0x42);
		groupInfoAll1.setGrpCountAttendByte((byte) 0x52);
		groupInfoAll1.setGrpCountTotalByte((byte) 0x62);
		groupInfoAll1.setGroupSeedInteger(0x72345678);
		groupInfoAll1.addIndexMemberInfoMapArray(memberInfoMap);
		System.out.println(groupInfoAll1.bytesToHexText());

		HwtxDataGroupInfoAll groupInfoAll2 = new HwtxDataGroupInfoAll();
		groupInfoAll2.fromBytes(groupInfoAll1.toBytes());
		System.out.printf("CrcGrpInfoAllBtApp: 0x%X\r\n",
				groupInfoAll2.getCrcGrpInfoAllBtAppShort());
		System.out.printf("SizeInByte: 0x%X\r\n",
				groupInfoAll2.getSizeInByteShort());
		System.out.printf("RtcTime: 0x%X\r\n",
				groupInfoAll2.getRtcTimeInteger());
		System.out.printf("GroupID: 0x%X\r\n", groupInfoAll2.getGroupIDShort());
		System.out.printf("GrpCountDevice: 0x%X\r\n",
				groupInfoAll2.getGrpCountDeviceByte());
		System.out.printf("GrpCountAttend: 0x%X\r\n",
				groupInfoAll2.getGrpCountAttendByte());
		System.out.printf("GrpCountTotal: 0x%X\r\n",
				groupInfoAll2.getGrpCountTotalByte());
		System.out.printf("GroupSeed: 0x%X\r\n",
				groupInfoAll2.getGroupSeedInteger());
		for (int i = 0; i < groupInfoAll2.getIndexMemberInfoMapArray().size(); ++i) {
			System.out.println(groupInfoAll2.getIndexMemberInfoMapArray()
					.get(i).bytesToHexText());
		}
		// gps表相关---------------------------------------------
		System.out.println("----准备传输GPS数据表----");
		try {
			HwtxCommandSendGpsPrepare sendGpsPrepare = new HwtxCommandSendGpsPrepare();
			sendGpsPrepare.setGpsDataLength(0x1F1A);
			System.out.println(sendGpsPrepare.bytesToHexText());
			System.out.printf("GpsDataLength: 0x%X",
					sendGpsPrepare.getGpsDataLength());
		} catch (HwtxCommandException e) {
			e.printStackTrace();
		}
		System.out.println("-------- HwtxDataGpsInfoDataComp");
		HwtxDataGpsInfoDataComp gpsInfoDataComp = new HwtxDataGpsInfoDataComp();
		gpsInfoDataComp.setGpsMiscByte((byte) 0x12);
		gpsInfoDataComp.setGpsTimeInteger(0x22345678);
		gpsInfoDataComp.setGpsLatitudeInteger(0x32345678);
		gpsInfoDataComp.setGpsLongitudeInteger(0x42345678);
		System.out.println(gpsInfoDataComp.bytesToHexText());

		System.out.println("-------- HwtxDataGrpGpsInfoItem");
		HwtxDataGrpGpsInfoItem grpGpsInfoItem = new HwtxDataGrpGpsInfoItem();
		grpGpsInfoItem.setAtMappingAddrByte((byte) 0x49);
		grpGpsInfoItem.setDevNumInGroupByte((byte) 0x59);
		grpGpsInfoItem.setGpsInfoDataComp(gpsInfoDataComp);
		System.out.println(grpGpsInfoItem.bytesToHexText());

		System.out.println("-------- HwtxDataGroupGPSInfoTable");
		HwtxDataGroupGPSInfoTable groupGPSInfoTable1 = new HwtxDataGroupGPSInfoTable();
		groupGPSInfoTable1.setGrpCountOfLogicNumByte((byte) 0x61);
		groupGPSInfoTable1.addIndexGpsInfoArray(grpGpsInfoItem);
		System.out.println(groupGPSInfoTable1.bytesToHexText());
		HwtxDataGroupGPSInfoTable groupGPSInfoTable2 = new HwtxDataGroupGPSInfoTable();
		groupGPSInfoTable2.fromBytes(groupGPSInfoTable1.toBytes());
		System.out.printf("GrpCountOfLogicNum: 0x%X\r\n",
				groupGPSInfoTable2.getGrpCountOfLogicNumByte());
	}

}
