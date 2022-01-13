import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankTransactionEntity = useAppSelector(state => state.bankTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankTransactionDetailsHeading">
          <Translate contentKey="akountsApp.bankTransaction.detail.title">BankTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.id}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="akountsApp.bankTransaction.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.transactionId}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="akountsApp.bankTransaction.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.transactionDate ? (
              <TextFormat value={bankTransactionEntity.transactionDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="akountsApp.bankTransaction.description">Description</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.description}</dd>
          <dt>
            <span id="localAmount">
              <Translate contentKey="akountsApp.bankTransaction.localAmount">Local Amount</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.localAmount}</dd>
          <dt>
            <span id="localCurrency">
              <Translate contentKey="akountsApp.bankTransaction.localCurrency">Local Currency</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.localCurrency}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.bankTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.amount}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="akountsApp.bankTransaction.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.currency}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="akountsApp.bankTransaction.note">Note</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.note}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="akountsApp.bankTransaction.year">Year</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.year}</dd>
          <dt>
            <span id="month">
              <Translate contentKey="akountsApp.bankTransaction.month">Month</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.month}</dd>
          <dt>
            <span id="week">
              <Translate contentKey="akountsApp.bankTransaction.week">Week</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.week}</dd>
          <dt>
            <span id="categorizedDate">
              <Translate contentKey="akountsApp.bankTransaction.categorizedDate">Categorized Date</Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.categorizedDate ? (
              <TextFormat value={bankTransactionEntity.categorizedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="addDate">
              <Translate contentKey="akountsApp.bankTransaction.addDate">Add Date</Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.addDate ? (
              <TextFormat value={bankTransactionEntity.addDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="checked">
              <Translate contentKey="akountsApp.bankTransaction.checked">Checked</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.checked ? 'true' : 'false'}</dd>
          <dt>
            <span id="rebasedDate">
              <Translate contentKey="akountsApp.bankTransaction.rebasedDate">Rebased Date</Translate>
            </span>
          </dt>
          <dd>
            {bankTransactionEntity.rebasedDate ? (
              <TextFormat value={bankTransactionEntity.rebasedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deleted">
              <Translate contentKey="akountsApp.bankTransaction.deleted">Deleted</Translate>
            </span>
          </dt>
          <dd>{bankTransactionEntity.deleted ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransaction.catId">Cat Id</Translate>
          </dt>
          <dd>{bankTransactionEntity.catId ? bankTransactionEntity.catId.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransaction.accountId">Account Id</Translate>
          </dt>
          <dd>{bankTransactionEntity.accountId ? bankTransactionEntity.accountId.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransaction.category">Category</Translate>
          </dt>
          <dd>{bankTransactionEntity.category ? bankTransactionEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTransaction.account">Account</Translate>
          </dt>
          <dd>{bankTransactionEntity.account ? bankTransactionEntity.account.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-transaction/${bankTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankTransactionDetail;
