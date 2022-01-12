import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaid-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaidItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaidItemEntity = useAppSelector(state => state.plaidItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaidItemDetailsHeading">
          <Translate contentKey="akountsApp.plaidItem.detail.title">PlaidItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaidItemEntity.id}</dd>
          <dt>
            <span id="itemId">
              <Translate contentKey="akountsApp.plaidItem.itemId">Item Id</Translate>
            </span>
          </dt>
          <dd>{plaidItemEntity.itemId}</dd>
          <dt>
            <span id="institutionId">
              <Translate contentKey="akountsApp.plaidItem.institutionId">Institution Id</Translate>
            </span>
          </dt>
          <dd>{plaidItemEntity.institutionId}</dd>
          <dt>
            <span id="accessToken">
              <Translate contentKey="akountsApp.plaidItem.accessToken">Access Token</Translate>
            </span>
          </dt>
          <dd>{plaidItemEntity.accessToken}</dd>
          <dt>
            <span id="addedDate">
              <Translate contentKey="akountsApp.plaidItem.addedDate">Added Date</Translate>
            </span>
          </dt>
          <dd>
            {plaidItemEntity.addedDate ? <TextFormat value={plaidItemEntity.addedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="akountsApp.plaidItem.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {plaidItemEntity.updateDate ? <TextFormat value={plaidItemEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/plaid-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaid-item/${plaidItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaidItemDetail;
