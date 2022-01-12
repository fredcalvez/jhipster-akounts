import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bridge-transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BridgeTransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bridgeTransactionEntity = useAppSelector(state => state.bridgeTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bridgeTransactionDetailsHeading">
          <Translate contentKey="akountsApp.bridgeTransaction.detail.title">BridgeTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.id}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="akountsApp.bridgeTransaction.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.transactionId}</dd>
          <dt>
            <span id="transactionType">
              <Translate contentKey="akountsApp.bridgeTransaction.transactionType">Transaction Type</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.transactionType}</dd>
          <dt>
            <span id="accountId">
              <Translate contentKey="akountsApp.bridgeTransaction.accountId">Account Id</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.accountId}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.bridgeTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.amount}</dd>
          <dt>
            <span id="isoCurrencyCode">
              <Translate contentKey="akountsApp.bridgeTransaction.isoCurrencyCode">Iso Currency Code</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.isoCurrencyCode}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="akountsApp.bridgeTransaction.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>
            {bridgeTransactionEntity.transactionDate ? (
              <TextFormat value={bridgeTransactionEntity.transactionDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="akountsApp.bridgeTransaction.description">Description</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.description}</dd>
          <dt>
            <span id="isFuture">
              <Translate contentKey="akountsApp.bridgeTransaction.isFuture">Is Future</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.isFuture ? 'true' : 'false'}</dd>
          <dt>
            <span id="isDeleted">
              <Translate contentKey="akountsApp.bridgeTransaction.isDeleted">Is Deleted</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.isDeleted ? 'true' : 'false'}</dd>
          <dt>
            <span id="addedDate">
              <Translate contentKey="akountsApp.bridgeTransaction.addedDate">Added Date</Translate>
            </span>
          </dt>
          <dd>
            {bridgeTransactionEntity.addedDate ? (
              <TextFormat value={bridgeTransactionEntity.addedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="akountsApp.bridgeTransaction.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {bridgeTransactionEntity.updatedAt ? (
              <TextFormat value={bridgeTransactionEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="checked">
              <Translate contentKey="akountsApp.bridgeTransaction.checked">Checked</Translate>
            </span>
          </dt>
          <dd>{bridgeTransactionEntity.checked ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/bridge-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bridge-transaction/${bridgeTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BridgeTransactionDetail;
