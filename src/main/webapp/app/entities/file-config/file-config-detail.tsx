import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './file-config.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FileConfigDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fileConfigEntity = useAppSelector(state => state.fileConfig.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fileConfigDetailsHeading">
          <Translate contentKey="akountsApp.fileConfig.detail.title">FileConfig</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.id}</dd>
          <dt>
            <span id="filename">
              <Translate contentKey="akountsApp.fileConfig.filename">Filename</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.filename}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="akountsApp.fileConfig.description">Description</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.description}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="akountsApp.fileConfig.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.amount}</dd>
          <dt>
            <span id="amountIn">
              <Translate contentKey="akountsApp.fileConfig.amountIn">Amount In</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.amountIn}</dd>
          <dt>
            <span id="amountOut">
              <Translate contentKey="akountsApp.fileConfig.amountOut">Amount Out</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.amountOut}</dd>
          <dt>
            <span id="accountNum">
              <Translate contentKey="akountsApp.fileConfig.accountNum">Account Num</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.accountNum}</dd>
          <dt>
            <span id="transactionDate">
              <Translate contentKey="akountsApp.fileConfig.transactionDate">Transaction Date</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.transactionDate}</dd>
          <dt>
            <span id="dateFormat">
              <Translate contentKey="akountsApp.fileConfig.dateFormat">Date Format</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.dateFormat}</dd>
          <dt>
            <span id="fieldSeparator">
              <Translate contentKey="akountsApp.fileConfig.fieldSeparator">Field Separator</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.fieldSeparator}</dd>
          <dt>
            <span id="hasHeader">
              <Translate contentKey="akountsApp.fileConfig.hasHeader">Has Header</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.hasHeader}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="akountsApp.fileConfig.note">Note</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.note}</dd>
          <dt>
            <span id="locale">
              <Translate contentKey="akountsApp.fileConfig.locale">Locale</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.locale}</dd>
          <dt>
            <span id="multipleAccount">
              <Translate contentKey="akountsApp.fileConfig.multipleAccount">Multiple Account</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.multipleAccount}</dd>
          <dt>
            <span id="encoding">
              <Translate contentKey="akountsApp.fileConfig.encoding">Encoding</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.encoding}</dd>
          <dt>
            <span id="sign">
              <Translate contentKey="akountsApp.fileConfig.sign">Sign</Translate>
            </span>
          </dt>
          <dd>{fileConfigEntity.sign}</dd>
        </dl>
        <Button tag={Link} to="/file-config" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file-config/${fileConfigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FileConfigDetail;
