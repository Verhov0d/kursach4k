import 'dart:io';
import 'package:path_provider/path_provider.dart' as path_provider;
import 'package:path/path.dart';
import 'package:prkt2pah/data/model/dvigatel.dart';
import 'package:prkt2pah/data/model/car.dart';
import 'package:prkt2pah/data/model/korobka.dart';
import 'package:prkt2pah/data/model/korzina.dart';
import 'package:prkt2pah/data/model/marka.dart';
import 'package:prkt2pah/data/model/postavshik.dart';
import 'package:prkt2pah/data/model/zavod.dart';
import 'package:prkt2pah/domain/entity/role_entity.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

import '../../common/data_base_request.dart';
import '../../data/model/role.dart';
import '../../data/model/user.dart';

class DataBaseHelper {
  static final DataBaseHelper instance = DataBaseHelper._instance();

  DataBaseHelper._instance();

  late final Directory _appDocumentDirectory;
  late final String _pathDB;
  late final Database database;
  final int _version = 15;

  Future<void> init() async {
    _appDocumentDirectory =
        await path_provider.getApplicationDocumentsDirectory();

    _pathDB = join(_appDocumentDirectory.path, 'car.db');

    if (Platform.isLinux || Platform.isWindows) {
      sqfliteFfiInit();
      database = await databaseFactoryFfi.openDatabase(_pathDB,
          options: OpenDatabaseOptions(
            version: _version,
            onUpgrade: (db, oldVersion, newVersion) async =>
              await onUpgradeTable(db),
          onCreate: (db, version) async {
            await onCreateTable(db);
          },
        ));
    } else {
      database = await openDatabase(
        _pathDB, 
        version: _version,
        onUpgrade: (db, oldVersion, newVersion) => onUpgradeTable(db),
        onCreate: (db, version) async {
        await onCreateTable(db);
      });
    }
  }

  Future<void> onCreateTable(Database db) async {
    for (var table in DataBaseRequest.tableCreateList) {
      await db.execute(table);
    }

    await onInitTable(db);
  }

  Future<void> onInitTable(Database db) async {
    try {
      db.insert(DataBaseRequest.tableRole, Role(role: "??????????????????????????").toMap());
      db.insert(DataBaseRequest.tableRole, Role(role: "????????????????????????").toMap());

      db.insert(
          DataBaseRequest.tableUser,
          User(
                  login: "9",
                  password: "9",
                  idRole: RoleEnum.admin)
              .toMap());

      // db.insert(DataBaseRequest.tableMarka, Marka(name: "????????????").toMap());
      // db.insert(DataBaseRequest.tableMarka, Marka(name: "????????").toMap());
      // db.insert(DataBaseRequest.tableMarka, Marka(name: "????????").toMap());

      // db.insert(DataBaseRequest.tablePostavshik, Postavshik(name: "??????????????").toMap());
      // db.insert(DataBaseRequest.tablePostavshik, Postavshik(name: "??????????").toMap());
      // db.insert(DataBaseRequest.tablePostavshik, Postavshik(name: "????????????").toMap());
      // db.insert(DataBaseRequest.tablePostavshik, Postavshik(name: "??????????").toMap());
      // db.insert(DataBaseRequest.tablePostavshik, Postavshik(name: "????????????????").toMap());

      // db.insert(DataBaseRequest.tableZavod, Zavod(name: "??????????????????").toMap());
      // db.insert(DataBaseRequest.tableZavod, Zavod(name: "??????????????????????").toMap());
      // db.insert(DataBaseRequest.tableZavod, Zavod(name: "??????????????????").toMap());

      // db.insert(DataBaseRequest.tableDvigatel, Dvigatel(name: "???????????????????? ??2").toMap());
      // db.insert(DataBaseRequest.tableDvigatel, Dvigatel(name: "?????????????????? 999").toMap());
      // db.insert(DataBaseRequest.tableDvigatel, Dvigatel(name: "???????????????? 01").toMap());

      // db.insert(DataBaseRequest.tableKorobka, Korobka(name: "?????????????? 10").toMap());
      // db.insert(DataBaseRequest.tableKorobka, Korobka(name: "???????????????? 3").toMap());
      // db.insert(DataBaseRequest.tableKorobka, Korobka(name: "???????????????? ??????????").toMap());

      // db.insert(
      //     DataBaseRequest.tableCar,
      //     Car(
      //             color: "????????????",
      //             weight: 500,
      //             type: "????????????????",
      //             vmestimost: 5,
      //             idDvigatel: 1,
      //             idKorobka: 2,
      //             idMarka: 2,
      //             idPostavshik: 2,
      //             idZavod: 5)
      //         .toMap());
      // db.insert(
      //     DataBaseRequest.tableCar,
      //     Car(
      //             color: "??????????????",
      //             weight: 1500,
      //             type: "????????????????",
      //             vmestimost: 20,
      //             idDvigatel: 2,
      //             idKorobka: 1,
      //             idMarka: 1,
      //             idPostavshik: 1,
      //             idZavod: 5)
      //         .toMap());

      // db.insert(DataBaseRequest.tableKorzina, Korzina(kolichestvo: 1, idCar: 1, idUser: 1).toMap());
      // db.insert(DataBaseRequest.tableKorzina, Korzina(kolichestvo: 2, idCar: 2, idUser: 2).toMap());

    } on DatabaseException catch (e) {}
  }

  Future<void> onUpgradeTable(Database db) async {
    var tables = await db.rawQuery('SELECT name  FROM sqlite_master;');
    for (var table in DataBaseRequest.tableList.reversed) {
      if (tables.where((element) => element['name'] == table).isNotEmpty) {
        await db.execute(DataBaseRequest.deleteTable(table));
      }
    }
    for (var createTable in DataBaseRequest.tableCreateList) {
      await db.execute(createTable);
    }
    await onInitTable(db);  }

  Future<void> onDropDataBase() async {
    database.close();

    if (Platform.isWindows) {
      databaseFactoryFfi.deleteDatabase(_pathDB);
    } else {
      await deleteDatabase(_pathDB);
    }
  }
}