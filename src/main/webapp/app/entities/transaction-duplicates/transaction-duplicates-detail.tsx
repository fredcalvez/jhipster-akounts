import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './transaction-duplicates.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TransactionDuplicatesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const transactionDuplicatesEntity = useAppSelector(state => state.transactionDuplicates.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDuplicatesDetailsHeading">
          <Translate contentKey="akountsApp.transactionDuplicates.detail.title">TransactionDuplicates</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transactionDuplicatesEntity.id}</dd>
          <dt>
            <span id="isduplicate">
              <Translate contentKey="akountsApp.transactionDuplicates.isduplicate">Isduplicate</Translate>
            </span>
          </dt>
          <dd>{transactionDuplicatesEntity.isduplicate ? 'true' : 'false'}</dd>
          <dt>
            <span id="dateAdd">
              <Translate contentKey="akountsApp.transactionDuplicates.dateAdd">Date Add</Translate>
            </span>
          </dt>
          <dd>
            {transactionDuplicatesEntity.dateAdd ? (
              <TextFormat value={transactionDuplicatesEntity.dateAdd} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="action">
              <Translate contentKey="akountsApp.transactionDuplicates.action">Action</Translate>
            </span>
          </dt>
          <dd>{transactionDuplicatesEntity.action}</dd>
          <dt>
            <span id="checked">
              <Translate contentKey="akountsApp.transactionDuplicates.checked">Checked</Translate>
            </span>
          </dt>
          <dd>{transactionDuplicatesEntity.checked ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="akountsApp.transactionDuplicates.parentTransactionId">Parent Transaction Id</Translate>
          </dt>
          <dd>{transactionDuplicatesEntity.parentTransactionId ? transactionDuplicatesEntity.parentTransactionId.id : ''}</dd>
          <dt>
            <Translate contentKey="akountsApp.transactionDuplicates.childTransactionId">Child Transaction Id</Translate>
          </dt>
          <dd>{transactionDuplicatesEntity.childTransactionId ? transactionDuplicatesEntity.childTransactionId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/transaction-duplicates" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction-duplicates/${transactionDuplicatesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransactionDuplicatesDetail;
