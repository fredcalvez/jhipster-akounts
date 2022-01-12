import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaid-transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidTransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaidTransactionEntity = useAppSelector(state => state.plaidTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaidTransactionDetailsHeading">
          <Translate contentKey="akountsApp.plaidTransaction.detail.title">PlaidTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.id}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="akountsApp.plaidTransaction.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.transactionId}</dd>
          <dt>
            <span id="transactionType">
              <Translate contentKey="akountsApp.plaidTransaction.transactionType">Transaction Type</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.transactionType}</dd>
          <dt>
            <span id="accountId">
              <Translate contentKey="akountsApp.plaidTransaction.accountId">Account Id</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.accountId}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.plaidTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.amount}</dd>
          <dt>
            <span id="isoCurrencyCode">
              <Translate contentKey="akountsApp.plaidTransaction.isoCurrencyCode">Iso Currency Code</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.isoCurrencyCode}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="akountsApp.plaidTransaction.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>
            {plaidTransactionEntity.transactionDate ? (
              <TextFormat value={plaidTransactionEntity.transactionDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="name">
              <Translate contentKey="akountsApp.plaidTransaction.name">Name</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.name}</dd>
          <dt>
            <span id="originalDescription">
              <Translate contentKey="akountsApp.plaidTransaction.originalDescription">Original Description</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.originalDescription}</dd>
          <dt>
            <span id="pending">
              <Translate contentKey="akountsApp.plaidTransaction.pending">Pending</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.pending ? 'true' : 'false'}</dd>
          <dt>
            <span id="pendingTransactionId">
              <Translate contentKey="akountsApp.plaidTransaction.pendingTransactionId">Pending Transaction Id</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.pendingTransactionId}</dd>
          <dt>
            <span id="addedDate">
              <Translate contentKey="akountsApp.plaidTransaction.addedDate">Added Date</Translate>
            </span>
          </dt>
          <dd>
            {plaidTransactionEntity.addedDate ? (
              <TextFormat value={plaidTransactionEntity.addedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="checked">
              <Translate contentKey="akountsApp.plaidTransaction.checked">Checked</Translate>
            </span>
          </dt>
          <dd>{plaidTransactionEntity.checked ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/plaid-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaid-transaction/${plaidTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaidTransactionDetail;
