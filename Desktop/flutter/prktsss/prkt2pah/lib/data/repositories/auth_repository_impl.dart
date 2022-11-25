import 'package:dartz/dartz.dart';
import 'package:sqflite/sqflite.dart';

import '../../common/data_base_request.dart';
import '../../core/crypto/crypto.dart';
import '../../core/db/data_base_helper.dart';
import '../../core/exception/failure.dart';
import '../../domain/entity/role_entity.dart';
import '../../domain/repositories/auth_repositories.dart';
import '../model/user.dart';

class AuthRepositoryImlp implements AuthRepositories {
  final _db = DataBaseHelper.instance.database;

  @override
  String get table => DataBaseRequest.tableUser;

  @override
  Future<Either<Failure, RoleEnum>> signIn(
      String login, String password) async {
    try {
      var user = await _db.query(
        table,
        columns: ['login', 'password', 'id_role'],
        where: 'login = ?',
        whereArgs: [login],
      );
      if (user.isEmpty) {
        return Left(AuthUserEmptyFailure());
      }

      if (user.first['password'] != Crypto.crypto(password)) {
        return Left(AuthPasswordFailure());
      }

      return Right(RoleEnum.values[(user.first['id_role'] as int) - 1]);
    } on DatabaseException catch (e) {
      Left(FailureImpl(e.getResultCode()!));
    }
    return Left(DefaultFailure());
  }

  @override
  Future<Either<Failure, bool>> signUp(String login, String password) async {
    try {
      await _db.insert(
        table,
        User(
          login: login,
          password: password,
          idRole: RoleEnum.user,
        ).toMap(),
      );
      return const Right(true);
    } on DatabaseException catch (e) {
      return Left(FailureImpl(e.getResultCode()!).error);
    }
  }
}