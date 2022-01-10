import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-account.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankAccountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankAccountEntity = useAppSelector(state => state.bankAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankAccountDetailsHeading">
          <Translate contentKey="akountsApp.bankAccount.detail.title">BankAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.id}</dd>
          <dt>
            <span id="accountLabel">
              <Translate contentKey="akountsApp.bankAccount.accountLabel">Account Label</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.accountLabel}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="akountsApp.bankAccount.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.accountNumber}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="akountsApp.bankAccount.active">Active</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="akountsApp.bankAccount.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.currency}</dd>
          <dt>
            <span id="initialAmount">
              <Translate contentKey="akountsApp.bankAccount.initialAmount">Initial Amount</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.initialAmount}</dd>
          <dt>
            <span id="initialAmountLocal">
              <Translate contentKey="akountsApp.bankAccount.initialAmountLocal">Initial Amount Local</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.initialAmountLocal}</dd>
          <dt>
            <span id="accountType">
              <Translate contentKey="akountsApp.bankAccount.accountType">Account Type</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.accountType}</dd>
          <dt>
            <span id="interest">
              <Translate contentKey="akountsApp.bankAccount.interest">Interest</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.interest}</dd>
          <dt>
            <span id="nickname">
              <Translate contentKey="akountsApp.bankAccount.nickname">Nickname</Translate>
            </span>
          </dt>
          <dd>{bankAccountEntity.nickname}</dd>
        </dl>
        <Button tag={Link} to="/bank-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-account/${bankAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankAccountDetail;
