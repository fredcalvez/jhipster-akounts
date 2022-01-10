import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-stream-transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankStreamTransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankStreamTransactionEntity = useAppSelector(state => state.bankStreamTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankStreamTransactionDetailsHeading">
          <Translate contentKey="akountsApp.bankStreamTransaction.detail.title">BankStreamTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankStreamTransactionEntity.id}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankStreamTransaction.transactionId">Transaction Id</Translate>
          </dt>
          <dd>{bankStreamTransactionEntity.transactionId ? bankStreamTransactionEntity.transactionId.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankStreamTransaction.streamId">Stream Id</Translate>
          </dt>
          <dd>{bankStreamTransactionEntity.streamId ? bankStreamTransactionEntity.streamId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-stream-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-stream-transaction/${bankStreamTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankStreamTransactionDetail;
