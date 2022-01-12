import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bank-tag.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankTagDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bankTagEntity = useAppSelector(state => state.bankTag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankTagDetailsHeading">
          <Translate contentKey="akountsApp.bankTag.detail.title">BankTag</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankTagEntity.id}</dd>
          <dt>
            <span id="tag">
              <Translate contentKey="akountsApp.bankTag.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{bankTagEntity.tag}</dd>
          <dt>
            <span id="useCount">
              <Translate contentKey="akountsApp.bankTag.useCount">Use Count</Translate>
            </span>
          </dt>
          <dd>{bankTagEntity.useCount}</dd>
          <dt>
            <Translate contentKey="akountsApp.bankTag.vendor">Vendor</Translate>
          </dt>
          <dd>{bankTagEntity.vendor ? bankTagEntity.vendor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-tag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-tag/${bankTagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankTagDetail;
